package com.elpaso.serfj.serializers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

/**
 * Default Json serializer/deserializer.
 * 
 * @author: Eduardo Yáñez Date: 14-may-2009
 */
public class JsonSerializer implements Serializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonSerializer.class);

	/**
	 * Serializes an object to Json.
	 */
	public String serialize(Object object) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Serializing object to Json");
		}
		XStream xstream = new XStream(new JettisonMappedXmlDriver());
		String json = xstream.toXML(object);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Object serialized well");
		}
		return json;
	}

	/**
	 * Deserializes a Json string representation to an object.
	 */
	public Object deserialize(String jsonObject) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Deserializing Json object");
		}
		XStream xstream = new XStream(new JettisonMappedXmlDriver());
		Object obj = xstream.fromXML(jsonObject);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Object deserialized");
		}
		return obj;
	}

	/**
	 * Returns "application/json" content-type.
	 */
	public String getContentType() {
		return "application/json";
	}

	/**
	 * Returns 'json' extension.
	 */
	public String getExtension() {
		return "json";
	}
}
