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

import junit.framework.TestCase;
import net.sf.serfj.finders.ResourceFinder;

import org.junit.Test;

/**
 * @author: Eduardo Yáñez 
 * Date: 09-may-2009
 */
public class ResourceFinderTest extends TestCase {
	@Test
	public void testFindResource() {
		ResourceFinder finder = new ResourceFinder("net.sf.serfj.test", "controllers", "Controller", "");
		// Find com.elpaso.test.controllers.BookController without package style defined
		String controller = finder.findResource("books");
		assertEquals("net.sf.serfj.test.controllers.BookController", controller);

		finder = new ResourceFinder("net.sf.serfj.test", "controllers", "Controller", "functional_by_resource");
		// Find com.elpaso.test.screen.controllers.ScreenController with functional by resource package style defined
		controller = finder.findResource("screens");
		assertEquals("net.sf.serfj.test.screen.controllers.ScreenController", controller);

		finder = new ResourceFinder("net.sf.serfj.test", "controllers", "Controller", "resource");
		// Find com.elpaso.test.key.KeyController with resource package style defined
		controller = finder.findResource("keys");
		assertEquals("net.sf.serfj.test.key.KeyController", controller);

		finder = new ResourceFinder("net.sf.serfj.test", "ctrl", "Controller", "functional_by_resource");
		// Don't find anything
		controller = finder.findResource("account");
		assertNull(controller);
	}
}
