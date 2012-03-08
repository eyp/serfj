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

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;
import net.sf.serfj.serializers.Base64Serializer;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpNotFoundException;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * @author Eduardo Yáñez
 */
public class RestServletTest extends TestCase {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestServletTest.class);

	private ServletRunner sr;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	@Override
	public void setUp() throws Exception {
		InputStream webXml = this.getClass().getResourceAsStream("/web.xml");
		sr = new ServletRunner(webXml);
	}

	@Test
	public void testService() {
        testGet("banks", "bank/index.html", "text/html");
		testGet("banks/1", "bank/show.html", "text/html");
		testGet("banks/1/accounts", "account/index.html", "text/html");
		testGet("banks/1/accounts/new", "account/new.html", "text/html");
		testGet("banks/1/accounts/1/balance", "account/balance.jsp", "text/html");
		testGet("banks/1/accounts/1/balance.json", "account/balance.jsp", "application/json");
        testGet64("banks/1/accounts/2.base64", "Account object to serialize", "application/octect-stream");
        testGet64("banks/1/accounts/1/balance.base64", "Balance object to serialize", "application/octect-stream");
	}

    /**
     * Tests a GET request. Receives an URL to test, and the page that
     * controller must respond.
     * 
     * @param requestedUrl
     *            - URL to test.
     * @param expectedPage
     *            - Expected response from the requested URL.
     */
    private void testGet(String requestedUrl, String expectedPage, String contentType) {
        ServletUnitClient sc = sr.newClient();
        try {
            WebRequest request = new GetMethodWebRequest("http://test.meterware.com/" + requestedUrl);
            WebResponse response = sc.getResponse(request);
            assertNotNull("No response received", response);
            assertEquals("content type", contentType, response.getContentType());
        } catch (HttpNotFoundException e) {
            assertPage(expectedPage, e);
        } catch (StringIndexOutOfBoundsException e) {
            fail("Page not found");
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            fail(e.getLocalizedMessage());
        }
    }

    /**
     * Tests a GET request. Receives an URL to test, and the page that
     * controller must respond.
     * 
     * @param requestedUrl
     *            - URL to test.
     * @param expectedPage
     *            - Expected response from the requested URL.
     */
    private void testGet64(String requestedUrl, String expectedContent, String contentType) {

        ServletUnitClient sc = sr.newClient();
        try {
            WebRequest request = new GetMethodWebRequest("http://test.meterware.com/" + requestedUrl);
            WebResponse response = sc.getResponse(request);
            assertNotNull("No response received", response);
            assertEquals("content type", contentType, response.getContentType());
            Base64Serializer serializer = new Base64Serializer();
            ByteArrayInputStream is = (ByteArrayInputStream) response.getInputStream();
            StringBuilder base64Object = new StringBuilder();
            while (is.available() > 0) {
                base64Object.append((char) is.read());
            }
            String deserialized = (String) serializer.deserialize(base64Object.toString());
            assertNotNull(deserialized);
        } catch (StringIndexOutOfBoundsException e) {
            fail("Page not found");
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            fail(e.getLocalizedMessage());
        }
    }

	/**
	 * If the page expected is not found (HTTP 404 error), then is ok, else, the
	 * test can't pass.
	 * 
	 * @param expectedPage
	 *            - Expected page.
	 * @param e
	 *            - Exception.
	 */
	private void assertPage(String expectedPage, HttpNotFoundException e) {
		if (e.getResponseCode() == 404) {
			assertTrue(e.getLocalizedMessage().indexOf("[http://localhost/views/" + expectedPage + "]") > 0);
		} else {
			fail(e.getLocalizedMessage());
		}
	}
}
