package com.elpaso.serfj.serializers;

import java.io.Serializable;

import com.thoughtworks.xstream.XStream;

/**
 * Default XML serializer/deserializer.
 * 
 * @author: Eduardo Yáñez
 * Date: 14-may-2009
 */
public class XmlSerializer implements Serializer {
    
    /**
     * Serializes an object to XML using the default XStream converter.
     *   
     * @see com.elpaso.serfj.serializers.Serializer#serialize(java.io.Serializable)
     */
    public String serialize(Serializable object) {
        XStream xstream = new XStream();
        return xstream.toXML(object);
    }

    /**
     * Deserializes a XML (default XStream representation) representation of an object.
     * 
     * @see com.elpaso.serfj.serializers.Serializer#deserialize(java.lang.String)
     */
    public Object deserialize(String string) {
        XStream xstream = new XStream();
        return xstream.fromXML(string);
    }

    /**
     * Returns "text/xml" content-type.
     * 
     * @see com.elpaso.serfj.serializers.Serializer#getContentType()
     */
    public String getContentType() {
        return "text/xml";
    }

    /**
     * Returns 'xml' extension.
     *  
     * @see com.elpaso.serfj.serializers.Serializer#getExtension()
     */
    public String getExtension() {
        return "xml";
    }

}
