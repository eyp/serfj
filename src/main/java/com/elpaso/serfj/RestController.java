/**
 * 
 */
package com.elpaso.serfj;

import java.io.IOException;
import java.util.Map;

/**
 * Class that can be used as parent class for an action which wants to be a
 * Serfj controller.
 * 
 * @author Eduardo Yáñez
 */
public class RestController {
	private ResponseHelper response;

	protected RestController() {
	}

	protected RestController(ResponseHelper response) {
		this.response = response;
	}

	public void setResponseHelper(ResponseHelper response) {
		this.response = response;
	}

	protected Map<String, Object> getParams() {
		return this.response.getParams();
	}

	protected Object getParam(String name) {
		return this.response.getParam(name);
	}

	/**
	 * Returns parameter's value that is a String.
	 * 
	 * @param name
	 *            Parameter's name.
	 * @return Parameter's value.
	 * @throws ClassCastException
	 *             if the value is not a {@link String}.
	 */
	protected String getStringParam(String name) {
		return (String) this.response.getParam(name);
	}

	protected String getId(String resource) {
		return this.response.getId(resource);
	}

	/**
	 * Renders the predefined page.
	 * 
	 * @throws IOException
	 *             if the page doesn't exist.
	 */
	public void renderPage() throws IOException {
		this.response.renderPage();
	}

	/**
	 * Renders some page within 'views' directory.
	 * 
	 * @param page
	 *            The page could have an extension or not. If it doesn't have an
	 *            extension, the framework first looks for page.jsp, then .html
	 *            or .htm extension.
	 * 
	 * @throws IOException
	 *             if the page doesn't exist.
	 */
	public void renderPage(String page) throws IOException {
		this.response.renderPage(page);
	}

	/**
	 * Renders a page from a resource.
	 * 
	 * @param resource
	 *            The name of the resource (bank, account, etc...). It must
	 *            exists below /views directory.
	 * @param page
	 *            The page can have an extension or not. If it doesn't have an
	 *            extension, the framework first looks for page.jsp, then with
	 *            .html or .htm extension.
	 * 
	 * @throws IOException
	 *             if the page doesn't exist.
	 */
	public void renderPage(String resource, String page) throws IOException {
		this.response.renderPage(resource, page);
	}

	/**
	 * Serialize an object. Serializer class used to process the object can be
	 * known using ResponseHelper.getSerializer() method.
	 * 
	 * @param object
	 *            Object to serialize.
	 */
	public void serialize(Object object) {
		this.response.serialize(object);
	}

	/**
	 * Gets the URL extension, if any.
	 * 
	 * @return An extension or an empty String if there isn't anyone.
	 */
	public String getExtension() {
		return this.response.getExtension();
	}

	/**
	 * Gets the Serializer class, if any.
	 * 
	 * @return A String with the fully qualified name of the serializer class
	 *         that will be used in case of needed to serialize an object in the
	 *         response.
	 */
	public String getSerializer() {
		return this.response.getSerializer();
	}
}