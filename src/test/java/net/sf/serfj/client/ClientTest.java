/*
 * Copyright 2010 Eduardo Yáñez Parareda
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.serfj.client;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.ConnectException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link Client} class.
 */
public class ClientTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for {@link net.sf.serfj.client.Client#postRequest(java.lang.String, java.util.Map)} .
     */
    @Test
    public void testPostRequest() {
    }

    @Test
    public void testServerDown() throws IOException, WebServiceException {
        try {
            Client client = new Client("http://localhost:1234");
            client.getRequest("", null);
            fail("Server doesn't exist, you can't reach this line");
        } catch (ConnectException e) {
            // Ok
            assertTrue(true);
        }
    }
}
