package com.elpaso.serfj.finders;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * @author: Eduardo Yáñez Date: 09-may-2009
 */
public class ResourceFinderTest extends TestCase {
	@Test
	public void testFindResource() {
		ResourceFinder finder = new ResourceFinder("com.elpaso.serfj.test", "controllers", "Controller", "");
		// Find com.elpaso.test.controllers.BankController without package style
		// defined
		String controller = finder.findResource("banks");
		assertEquals("com.elpaso.serfj.test.controllers.BankController", controller);

		finder = new ResourceFinder("com.elpaso.serfj.test", "controllers", "Controller", "functional_by_resource");
		// Find com.elpaso.test.account.controllers.AccountController with
		// functional by resource package style defined
		controller = finder.findResource("account");
		assertEquals("com.elpaso.serfj.test.account.controllers.AccountController", controller);

		finder = new ResourceFinder("com.elpaso.serfj.test", "controllers", "Controller", "resource");
		// Find com.elpaso.test.holder.HolderController with resource package
		// style defined
		controller = finder.findResource("holder");
		assertEquals("com.elpaso.serfj.test.holder.HolderController", controller);

		finder = new ResourceFinder("com.elpaso.serfj.test", "ctrl", "Controller", "functional_by_resource");
		// Don't find anything
		controller = finder.findResource("account");
		assertNull(controller);
	}
}
