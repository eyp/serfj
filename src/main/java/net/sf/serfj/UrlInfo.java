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

import java.util.HashMap;
import java.util.Map;

/**
 * Represents all the information extracted from an URL. The main Identifier,
 * controller, secondary identifiers, serializer, etc...
 * 
 * @author: Eduardo Yáñez Date: 10-may-2009
 */
class UrlInfo {
	private String url;
	private HttpMethod requestMethod;
	private String resource;
	private String serializer;
	private String controller;
	private String action;
	private String extension;
	private Map<String, String> identifiers;

	public UrlInfo(String url, HttpMethod requestMethod) {
		this.url = url;
		this.identifiers = new HashMap<String, String>();
		this.requestMethod = requestMethod;
	}

	public void addId(String resource, String id) {
		this.identifiers.put(resource + "_id", id);
	}

	public void addId(String id) {
		this.identifiers.put("id", id);
	}

	public Map<String, String> getIdentifiers() {
		return this.identifiers;
	}

	public String getId(String resource) {
		return this.identifiers.get(resource + "_id");
	}

	public String getId() {
		return this.identifiers.get("id");
	}

	public String getUrl() {
		return url;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getSerializer() {
		return serializer;
	}

	public void setSerializer(String serializer) {
		this.serializer = serializer;
	}

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public HttpMethod getRequestMethod() {
		return requestMethod;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		if (extension == null) {
			this.extension = "";
		} else {
			this.extension = extension;
		}
	}

	@Override
	public String toString() {
		return "UrlInfo{" + "url='" + url + '\'' + ", requestMethod='" + requestMethod + '\'' + ", identifiers='" + identifiers + '\'' + ", resource='" + resource + '\'' + ", extension='" + extension + '\'' + ", serializer='" + serializer + '\'' + ", controller='" + controller + '\'' + ", action='" + action + '\'' + '}';
	}
}
