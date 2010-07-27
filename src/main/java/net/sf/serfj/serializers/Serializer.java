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

/**
 * Interface for Serializers.
 * 
 * @author Eduardo Yáñez
 */
public interface Serializer {
	/**
	 * Serialize an object in the format that the implementation requires.
	 * 
	 * @param object
	 *            Object to serialize.
	 * @return a String with the object serialized.
	 */
	public String serialize(Object object);

	/**
	 * Deserialize an object from the format that the implementation requires to
	 * Java Object.
	 * 
	 * @param string
	 *            String representation of the object to deserialize.
	 * @return an Object.
	 */
	public Object deserialize(String string);

	/**
	 * Content type that will be used in the response.
	 */
	public String getContentType();

	/**
	 * Returns the extension which came in the URL. With that extension the
	 * framework knows which serializer must use for serialization.
	 * 
	 * @return a String with an extension without the dot.
	 */
	public String getExtension();
}
