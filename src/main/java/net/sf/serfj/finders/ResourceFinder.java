/*
 * Copyright 2010 Eduardo Yáñez Parareda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.serfj.finders;


import java.text.MessageFormat;

import net.sf.serfj.util.UrlUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to find some classes like controllers or serializers. 
 * It depends on how is configured the framework. If there is no configuration, it uses default values.<br><br>
 * 
 * The minimal configuration is the <em>main.package</em> property, which is the root to look for controllers and serializers.<br>
 * Other configuration properties are:<br>
 * <ul>
 *   <li>packages.style: It's the strategy used to search for controllers or serializers.</li>
 *   <li>alias.controllers.package: It's an alias to append to <em>main.package</em> property.</li>
 *   <li>suffix.controllers: It's the suffix that controllers' classes have.</li>
 *   <li>alias.serializers.package: It's an alias to append to <em>main.package</em> property.</li>
 *   <li>suffix.serializers: It's the suffix that serializers' classes have.</li>
 * </ul>
 * 
 * @author Eduardo Yáñez 
 */
public class ResourceFinder {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceFinder.class);

	private static final String MODEL_STYLE = "model";
	private static final String FUNCTIONAL_BY_MODEL_STYLE = "functional_by_model";
	private static final String FUNCTIONAL_STYLE = "functional";
	protected static final String OFF_OPTION = "OFF";

	/**
	 * Default package for classes used when some class is not found and there
	 * is a SERFJ implementation for something. For example, a XmlSerializer, a
	 * JsonSerializer, and so on.
	 */
	protected static final String DEFAULT_PACKAGE = "net.sf.serfj";

	private String mainPackage;
	private String alias;
	private String prefix;
	private String suffix;
	private String style;
	private UrlUtils utils = UrlUtils.getInstance();

	ResourceFinder(String mainPackage, String alias, String prefix, String suffix, String style) {
		this(mainPackage, alias, suffix, style);
		this.prefix = prefix.toLowerCase();
	}

	public ResourceFinder(String mainPackage, String alias, String suffix, String style) {
		if (LOGGER.isDebugEnabled()) {
			Object[] params = new Object[] { mainPackage, alias, suffix, style };
			LOGGER.debug("MainPackage [{}], Alias [{}], Suffix [{}], Style [{}]", params);
		}
		this.mainPackage = mainPackage;
		this.alias = alias;
		if (!OFF_OPTION.equals(suffix)) {
			this.suffix = suffix;
		}
		this.style = style;
	}

	public String findResource(String model) {
		if (model == null) {
			LOGGER.debug("Searching a default resource for no model");
			return this.defaultResource(null);
		}

		String clazz;
		if (FUNCTIONAL_STYLE.equals(style)) {
			clazz = findByFunction(model);
		} else if (FUNCTIONAL_BY_MODEL_STYLE.equals(style)) {
			clazz = findByFunctionAndModel(model);
		} else if (MODEL_STYLE.equals(style)) {
			clazz = findByModel(model);
		} else {
			// Style wasn't defined, so we search the class first as Functional style,
			// then as Functional by Resource style, and last by Resource
			clazz = findByFunction(model);
			if (clazz == null) {
				clazz = findByFunctionAndModel(model);
				if (clazz == null) {
					clazz = findByModel(model);
				}
			}
		}
		if (clazz == null) {
			LOGGER.debug("Searching a default resource for model [{}]", model);
			clazz = this.defaultResource(model);
		}
		return clazz;
	}

	/**
	 * Gets the default resource in case finder can't find an implementation.
	 * 
	 * @param model
	 *            - Model.
	 * @return always returns null. If a finder know there are default
	 *         resources, it must override this method.
	 */
	protected String defaultResource(String model) {
		return null;
	}

	private Boolean existsClass(String clazz) {
		Boolean exists = true;
		try {
			Class.forName(clazz);
		} catch (ClassNotFoundException e) {
			LOGGER.debug("Class {} doesn't exists in the Classpath", clazz);
			exists = false;
		}
		return exists;
	}

	protected String findByFunction(String model) {
        String clazz = MessageFormat.format("{0}.{1}.{2}", mainPackage, alias, this.makeClassName());
        LOGGER.debug("Searching resource [{}] by FUNCTIONAL style without resource name", clazz);

        if (!existsClass(clazz)) {
            clazz = MessageFormat.format("{0}.{1}.{2}", mainPackage, alias, this.makeClassName(model));
            LOGGER.debug("Searching resource [{}] by FUNCTIONAL style", clazz);
    		if (!existsClass(clazz)) {
    			return null;
    		}
        }
		return clazz;
	}

	protected String findByFunctionAndModel(String model) {
		String clazz = MessageFormat.format("{0}.{1}.{2}.{3}", mainPackage, utils.singularize(model), 
		        alias, this.makeClassName(model));
		LOGGER.debug("Searching resource [{}] by FUNCTIONAL BY MODEL style", clazz);
		if (!existsClass(clazz)) {
			return null;
		}
		return clazz;
	}

	protected String findByModel(String model) {
		String clazz = MessageFormat.format("{0}.{1}.{2}", mainPackage, utils.singularize(model), this.makeClassName(model));
		LOGGER.debug("Searching resource [{}] by MODEL style", clazz);
		if (!existsClass(clazz)) {
			return null;
		}
		return clazz;
	}

    protected String makeClassName(String model) {
        String clazz = MessageFormat.format("{0}{1}{2}", utils.capitalize(prefix), 
                utils.capitalize(utils.singularize(model)), utils.capitalize(suffix));
        return clazz;
    }

    protected String makeClassName() {
        String clazz = MessageFormat.format("{0}{1}", utils.capitalize(prefix), utils.capitalize(suffix));
        return clazz;
    }

	protected String getPrefix() {
		return this.prefix;
	}

	protected UrlUtils getUtils() {
		return this.utils;
	}
}
