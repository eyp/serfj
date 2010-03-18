package com.elpaso.serfj.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Utilidades para tratamiento de la url y de la información que viene en ella.
 * 
 * @author Eduardo Yáñez
 *
 */
public class UrlUtils {
    
    /**
     * Instance.
     */
    private static final UrlUtils instance = new UrlUtils();

    /**
     * Predefined plurals and singulars.
     */
    private Map<String, String> singulars = new HashMap<String, String>();

    /**
     * Contructor.
     */
    private UrlUtils() {
        super();
        singulars.put("people", "person");
        singulars.put("children", "child");
        singulars.put("women", "woman");
        singulars.put("men", "man");
        singulars.put("feet", "foot");
        singulars.put("teeth", "tooth");
        singulars.put("wives", "wife");
        singulars.put("lives", "life");
        singulars.put("knives", "knife");
        singulars.put("mice", "mouse");
    }

    /**
     * Returns the unique instance of this class.
     */
    public static UrlUtils getInstance() {
        return instance;
    }
   
    /**
     * Removes every character after '?'.
     *
     * @param string - String.
     * @return The received string or everything before a '?' character.
     */
    public String removeQueryString(String string) {
        if (string != null && string.matches(".*\\?.*")) {
            string = string.split("\\?")[0];
        }
        return string;
    }

    /**
     * Removes every character after '?'.
     *
     * @param string - String.
     * @return The received string or everything before a '?' character.
     */
    private String removeExtension(String string) {
        int dotIndex = string.lastIndexOf(".");
        if (dotIndex > 0) {
            string = string.substring(0, dotIndex);
        }
        return string;
    }

    public String cleanString(String string) {
        return this.removeQueryString(this.removeExtension(string));    
    }

    /**
     * Changes the first character to uppercase.
     * 
     * @param string - String.
     * @return Same string with first character in uppercase.
     */
    public String capitalize(String string) {
        if (string == null || string.length() < 1) {
            return "";
        }
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    /**
     * Checks if an element of the URL is an identifier.
     */
    public Boolean isIdentifier(String string) {
        if (string == null) {
            return false;
        }
        return string.matches("[0-9]*");
    }

    /**
     * Gets the singular of a plural.
     * 
     * @param noun - Name.
     * @return if the name was at singular, it does anything. If the name was a plural, returns its singular.
     */
    public String singularize(String noun) {
        String singular = noun;
        if (singulars.get(noun) != null) {
            singular = singulars.get(noun);
        } else if (noun.matches(".*is$")) {
            // Singular of *is => *es
            singular = noun.substring(0, noun.length() - 2) + "es";
        } else if (noun.matches(".*ies$")) {
            // Singular of *ies => *y
            singular = noun.substring(0, noun.length() - 3) + "y";
        } else if (noun.matches(".*ves$")) {
            // Singular of *ves => *f
            singular = noun.substring(0, noun.length() - 3) + "f";
        } else if (noun.matches(".*es$")) {
            if (noun.matches(".*les$")) {
                // Singular of *les =>
                singular = noun.substring(0, noun.length() - 1);
            } else {
                // Singular of *es =>
                singular = noun.substring(0, noun.length() - 2);
            }
        } else if (noun.matches(".*s$")) {
            // Singular of *s =>
            singular = noun.substring(0, noun.length() - 1);
        }
        return singular;
    }
}
