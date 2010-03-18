package com.elpaso.serfj;

import com.elpaso.serfj.util.UrlUtils;
import junit.framework.TestCase;
import org.junit.Before;


/**
 * @author Eduardo Yáñez
 *
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
