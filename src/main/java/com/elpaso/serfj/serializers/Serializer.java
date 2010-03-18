package com.elpaso.serfj.serializers;

import java.io.Serializable;

/**
 * Interface for Serializers.
 *
 * @author Eduardo Yáñez
 */
public interface Serializer {
    /**
     * Serialize an object in the format that the implementation requires.
     *
     * @param object - Object to serialize.
     * @return a String with the object serialized.
     */
    public String serialize(Serializable object);

    /**
     * Deserialize an object from the format that the implementation requires to Java Object.
     *
     * @param string - String representation of the object to deserialize.
     * @return an Object.
     */
    public Object deserialize(String string);

    /**
     * Content type that will be used in the response.
     */
    public String getContentType();

    /**
     * Returns the extension which came in the URL. With that extension the framework knows which
     * serializer must use for serialization.
     *
     * @return a String with an extension without the dot.
     */
    public String getExtension();
}
