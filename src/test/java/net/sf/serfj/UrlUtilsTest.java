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

import junit.framework.TestCase;
import net.sf.serfj.util.UrlUtils;

import org.junit.Before;

/**
 * @author Eduardo Yáñez
 */
public class UrlUtilsTest extends TestCase {

	private UrlUtils utils;

	/**
	 * @throws java.lang.Exception
	 */
	@Override
	@Before
	public void setUp() throws Exception {
		utils = UrlUtils.getInstance();
	}

	public void testSingularize() {
		assertEquals("sample", utils.singularize("samples"));
		assertEquals("bank", utils.singularize("banks"));
		assertEquals("bank", utils.singularize("bank"));
		assertEquals("class", utils.singularize("classes"));
		assertEquals("security", utils.singularize("securities"));
		assertEquals("person", utils.singularize("people"));
		assertEquals("child", utils.singularize("children"));
		assertEquals("leaf", utils.singularize("leaves"));
		assertEquals("axes", utils.singularize("axis"));
	}

	public void testIsIdentifier() {
		String stringToCheck = "9";
		assertTrue(utils.isIdentifier(stringToCheck));
		stringToCheck = "90";
		assertTrue(utils.isIdentifier(stringToCheck));
		stringToCheck = "907598347";
		assertTrue(utils.isIdentifier(stringToCheck));
		stringToCheck = "9075f983s7";
		assertFalse(utils.isIdentifier(stringToCheck));
		stringToCheck = "a9075f983s7";
		assertFalse(utils.isIdentifier(stringToCheck));
	}

}
