package com.elpaso.serfj.serializers;

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
     */
    public String serialize(Object object) {
        XStream xstream = new XStream();
        return xstream.toXML(object);
    }

    /**
     * Deserializes a XML (default XStream representation) representation of an object.
     */
    public Object deserialize(String string) {
        XStream xstream = new XStream();
        return xstream.fromXML(string);
    }

    /**
     * Returns "text/xml" content-type.
     */
    public String getContentType() {
        return "text/xml";
    }

    /**
     * Returns 'xml' extension.
     */
    public String getExtension() {
        return "xml";
    }

}
