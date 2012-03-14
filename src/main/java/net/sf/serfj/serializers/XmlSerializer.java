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
package net.sf.serfj.serializers;

import com.thoughtworks.xstream.XStream;

/**
 * Default XML serializer/deserializer.
 * 
 * @author Eduardo Yáñez
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
	 * Deserializes a XML (default XStream representation) representation of an
	 * object.
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
