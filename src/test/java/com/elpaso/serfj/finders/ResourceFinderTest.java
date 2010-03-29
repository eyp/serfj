package com.elpaso.serfj.finders;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * @author: Eduardo Yáñez 
 * Date: 09-may-2009
 */
public class ResourceFinderTest extends TestCase {
	@Test
	public void testFindResource() {
		ResourceFinder finder = new ResourceFinder("com.elpaso.serfj.test", "controllers", "Controller", "");
		// Find com.elpaso.test.controllers.BookController without package style defined
		String controller = finder.findResource("books");
		assertEquals("com.elpaso.serfj.test.controllers.BookController", controller);

		finder = new ResourceFinder("com.elpaso.serfj.test", "controllers", "Controller", "functional_by_resource");
		// Find com.elpaso.test.screen.controllers.ScreenController with functional by resource package style defined
		controller = finder.findResource("screens");
		assertEquals("com.elpaso.serfj.test.screen.controllers.ScreenController", controller);

		finder = new ResourceFinder("com.elpaso.serfj.test", "controllers", "Controller", "resource");
		// Find com.elpaso.test.key.KeyController with resource package style defined
		controller = finder.findResource("keys");
		assertEquals("com.elpaso.serfj.test.key.KeyController", controller);

		finder = new ResourceFinder("com.elpaso.serfj.test", "ctrl", "Controller", "functional_by_resource");
		// Don't find anything
		controller = finder.findResource("account");
		assertNull(controller);
	}
}
