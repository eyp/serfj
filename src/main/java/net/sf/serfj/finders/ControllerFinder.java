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
 * @author: Eduardo Yáñez 
 * Date: 08-may-2009
 */
public class ControllerFinder extends ResourceFinder {

	public ControllerFinder(Config config) {
		super(config.getString(Config.MAIN_PACKAGE), config.getString(Config.ALIAS_CONTROLLERS_PACKAGE), 
		        config.getString(Config.SUFFIX_CONTROLLER), config.getString(Config.PACKAGES_STYLE));
	}
}
