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

import net.sf.serfj.Config;

/**
 * This class is used to find a controller. It depends on how is configured the framework.
 * If there is no configuration for controllers, it uses default values.<br><br>
 * 
 * The minimal configuration is the <em>main.package</em> property, which is the root to look for controllers.<br>
 * Other configuration properties are:<br>
 * <ul>
 *   <li>alias.controller.package: It's an alias to append to <em>main.package</em> property.</li>
 *   <li>suffix.controllers: It's the suffix that controllers' classes have.</li>
 *   <li>packages.style: It's the strategy used to search for controllers.</li>
 * </ul>
 * 
 * @author Eduardo Yáñez 
 */
public class ControllerFinder extends ResourceFinder {

	public ControllerFinder(Config config) {
		super(config.getString(Config.MAIN_PACKAGE), config.getString(Config.ALIAS_CONTROLLERS_PACKAGE), 
		        config.getString(Config.SUFFIX_CONTROLLER), config.getString(Config.PACKAGES_STYLE));
	}
}
