package net.sf.serfj;

import junit.framework.TestCase;
import net.sf.serfj.Config;
import net.sf.serfj.HttpMethod;
import net.sf.serfj.UrlInfo;
import net.sf.serfj.UrlInspector;

import org.junit.Before;

/**
 * @author Eduardo Yáñez 
 * Date: 01-may-2009
 */
public class UrlInspectorTest extends TestCase {
	private UrlInspector inspector;

	/**
	 * @throws java.lang.Exception
	 */
	@Override
	@Before
	public void setUp() throws Exception {
		inspector = new UrlInspector(new Config("/config/serfj.properties"));
	}

	public void testGetSerializerClass() {
		assertEquals("net.sf.serfj.serializers.JsonSerializer", inspector.getSerializerClass("item", "items.json"));
		assertEquals("net.sf.serfj.test.serializers.JsonBankSerializer", inspector.getSerializerClass("bank", "bank.json"));
		assertEquals("net.sf.serfj.test.account.serializers.XmlAccountSerializer", inspector.getSerializerClass("account", "1.xml"));
		assertNull(inspector.getSerializerClass("item", "items"));
	}

	public void testGetUrlInfo() {
		UrlInfo info = inspector.getUrlInfo("/bank", HttpMethod.GET);
		assertEquals("bank", info.getResource());
		assertNull(info.getId());
		assertEquals("index", info.getAction());
		assertEquals("net.sf.serfj.test.controllers.Bank", info.getController());
		assertNull(info.getSerializer());

		info = inspector.getUrlInfo("/bank/1.json", HttpMethod.GET);
		assertEquals("bank", info.getResource());
		assertEquals("1", info.getId());
		assertEquals("show", info.getAction());
		assertEquals("net.sf.serfj.test.controllers.Bank", info.getController());
		assertEquals("net.sf.serfj.test.serializers.JsonBankSerializer", info.getSerializer());

		info = inspector.getUrlInfo("/bank/1", HttpMethod.DELETE);
		assertEquals("bank", info.getResource());
		assertEquals("1", info.getId());
		assertEquals("delete", info.getAction());
		assertEquals("net.sf.serfj.test.controllers.Bank", info.getController());
		assertNull(info.getSerializer());

		info = inspector.getUrlInfo("/bank/1/account.xml", HttpMethod.GET);
		assertEquals("account", info.getResource());
		assertEquals("1", info.getId("bank"));
		assertEquals("index", info.getAction());
		assertEquals("net.sf.serfj.test.account.controllers.Account", info.getController());
		assertEquals("net.sf.serfj.test.account.serializers.XmlAccountSerializer", info.getSerializer());

		info = inspector.getUrlInfo("/bank/1/account/2", HttpMethod.GET);
		assertEquals("account", info.getResource());
		assertEquals("2", info.getId());
		assertEquals("1", info.getId("bank"));
		assertEquals("show", info.getAction());
		assertEquals("net.sf.serfj.test.account.controllers.Account", info.getController());
		assertNull(info.getSerializer());

		info = inspector.getUrlInfo("/banks/1/accounts", HttpMethod.GET);
		assertEquals("account", info.getResource());
		assertEquals("1", info.getId("bank"));
		assertEquals("index", info.getAction());
		assertEquals("net.sf.serfj.test.account.controllers.Account", info.getController());
		assertNull(info.getSerializer());

		info = inspector.getUrlInfo("/bank/1/account/2", HttpMethod.PUT);
		assertEquals("account", info.getResource());
		assertEquals("2", info.getId());
		assertEquals("1", info.getId("bank"));
		assertEquals("update", info.getAction());
		assertEquals("net.sf.serfj.test.account.controllers.Account", info.getController());
		assertNull(info.getSerializer());

		info = inspector.getUrlInfo("/bank/1/account/2", HttpMethod.POST);
		assertEquals("account", info.getResource());
		assertEquals("2", info.getId());
		assertEquals("1", info.getId("bank"));
		assertEquals("create", info.getAction());
		assertEquals("net.sf.serfj.test.account.controllers.Account", info.getController());

		info = inspector.getUrlInfo("/bank/1/account/2/holder", HttpMethod.GET);
		assertEquals("holder", info.getResource());
		assertNull(info.getId());
		assertEquals("1", info.getId("bank"));
		assertEquals("2", info.getId("account"));
		assertEquals("index", info.getAction());
		assertEquals("net.sf.serfj.test.holder.Holder", info.getController());
		assertNull(info.getSerializer());

		info = inspector.getUrlInfo("/bank/1/account/2/holder/3/anotherAction", HttpMethod.GET);
		assertEquals("holder", info.getResource());
		assertEquals("3", info.getId());
		assertEquals("1", info.getId("bank"));
		assertEquals("2", info.getId("account"));
		assertEquals("anotherAction", info.getAction());
		assertEquals("net.sf.serfj.test.holder.Holder", info.getController());
		assertNull(info.getSerializer());

		info = inspector.getUrlInfo("/bank/1/edit", HttpMethod.GET);
		assertEquals("bank", info.getResource());
		assertEquals("1", info.getId());
		assertEquals("edit", info.getAction());
		assertEquals("net.sf.serfj.test.controllers.Bank", info.getController());
		assertNull(info.getSerializer());

		info = inspector.getUrlInfo("/bank/1/edit?param", HttpMethod.GET);
		assertEquals("bank", info.getResource());
		assertEquals("1", info.getId());
		assertEquals("edit", info.getAction());
		assertEquals("net.sf.serfj.test.controllers.Bank", info.getController());
		assertNull(info.getSerializer());

		info = inspector.getUrlInfo("/banks/1/accounts/2/edit?param", HttpMethod.GET);
		assertEquals("account", info.getResource());
		assertEquals("2", info.getId());
		assertEquals("1", info.getId("bank"));
		assertEquals("edit", info.getAction());
		assertEquals("net.sf.serfj.test.account.controllers.Account", info.getController());
		assertNull(info.getSerializer());

		info = inspector.getUrlInfo("/banks/new", HttpMethod.GET);
		assertEquals("bank", info.getResource());
		assertNull(info.getId());
		assertEquals("newResource", info.getAction());
		assertEquals("net.sf.serfj.test.controllers.Bank", info.getController());
		assertNull(info.getSerializer());
	}
}
