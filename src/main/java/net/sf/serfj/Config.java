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
package net.sf.serfj;

import net.sf.serfj.config.ConfigFileIOException;
import net.sf.serfj.config.ConfigParam;
import net.sf.serfj.config.SystemConfig;

/**
 * Framework configuration.
 * 
 * @author Eduardo Yáñez
 */
public class Config extends SystemConfig {

	/**
	 * Flag for debug purposes.
	 */
	protected static final ConfigParam DEBUG = new ConfigParam("debug");

	/**
	 * HTTP Encoding. Default is UTF-8
	 */
	public static final ConfigParam ENCODING = new ConfigParam("encoding", "UTF-8");

	/**
	 * Java main package where the source is located.
	 */
	public static final ConfigParam MAIN_PACKAGE = new ConfigParam("main.package");

	/**
	 * Packages style.
	 */
	public static final ConfigParam PACKAGES_STYLE = new ConfigParam("packages.style");

	/**
	 * Java package where controllers will be located.
	 */
	public static final ConfigParam ALIAS_CONTROLLERS_PACKAGE = new ConfigParam("alias.controllers.package", "controllers");

	/**
	 * Java package where serializers for responses will be located.
	 */
	public static final ConfigParam ALIAS_SERIALIZERS_PACKAGE = new ConfigParam("alias.serializers.package", "serializers");

	/**
	 * Suffix used for controller classes
	 */
	public static final ConfigParam SUFFIX_CONTROLLER = new ConfigParam("suffix.controllers", "OFF");

	/**
	 * Suffix used for serializer classes
	 */
	public static final ConfigParam SUFFIX_SERIALIZER = new ConfigParam("suffix.serializer", "Serializer");

	/**
	 * Directory where the views will be located.
	 */
	public static final ConfigParam VIEWS_DIRECTORY = new ConfigParam("views.directory", "views");

	public Config(String filename) throws ConfigFileIOException {
		super(filename);
	}
}
