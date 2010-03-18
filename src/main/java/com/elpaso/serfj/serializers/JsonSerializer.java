package com.elpaso.serfj.serializers;

import java.io.Serializable;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

/**
 * Default Json serializer/deserializer.
 * 
 * @author: Eduardo Yáñez
 * Date: 14-may-2009
 */
public class JsonSerializer implements Serializer {

    /**
     * Serializes an object to Json.
     * 
     * @see com.elpaso.serfj.serializers.Serializer#serialize(java.io.Serializable)
     */
    public String serialize(Serializable object) {
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        return xstream.toXML(object);
    }

    /**
     * Deserializes a Json string representation to an object.
     *  
     * @see com.elpaso.serfj.serializers.Serializer#deserialize(java.lang.String)
     */
    public Object deserialize(String jsonObject) {
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        return xstream.fromXML(jsonObject);
    }

    /**
     * Returns "application/json" content-type.
     * 
     * @see com.elpaso.serfj.serializers.Serializer#getContentType()
     */
    public String getContentType() {
        return "application/json";
    }

    /**
     * Returns 'json' extension.
     * 
     * @see com.elpaso.serfj.serializers.Serializer#getExtension()
     */
    public String getExtension() {
        return "json";
    }
}
