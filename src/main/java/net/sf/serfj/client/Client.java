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
package net.sf.serfj.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.rmi.ConnectException;
import java.util.Map;

import net.sf.serfj.HttpMethod;
import net.sf.serfj.finders.SerializerFinder;
import net.sf.serfj.util.UrlUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Client to manage REST calls to a REST Controller. It provides methods to make
 * POST, GET, PUT and DELETE HTTP request to any controller. It's necessary to
 * provide the resource's name (plural) and the action if anyone wants to be
 * invocated.
 * 
 * @author Eduardo Yáñez
 */
public class Client {

	private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

	private static final String CHARSET_ENCODING = "UTF-8";

	private String host;

	private StringBuilder urlString = new StringBuilder();

	/**
	 * Constructor. This constructor has to be used in case the server is in the
	 * same context as the client.
	 */
	public Client() {
		this("/");
	}

	/**
	 * Constructor.
	 * 
	 * @param host
	 *            Host which will receive the calls.
	 */
	public Client(String host) {
		super();
		this.host = host;
		// Si no termina en '/', hacemos que termine en '/'.
		if (host.lastIndexOf('/') != host.length() - 1 && host.length() != 1) {
			this.host += "/";
		}
	}

	/**
	 * Do a GET HTTP request to the given REST-URL.
	 * 
	 * @param restUrl
	 *            REST URL.
	 * @param params
	 *            Parameters for adding to the query string.
	 * @throws IOException
	 *             if the request go bad.
	 */
	public Object getRequest(String restUrl, Map<String, String> params) throws IOException, WebServiceException {
		HttpURLConnection conn = null;
		try {
			// Make the URL
			urlString = new StringBuilder(this.host).append(restUrl);
			urlString.append(this.makeParamsString(params, true));
			if (LOGGER.isDebugEnabled()) {
			    LOGGER.debug("Doing HTTP request: GET [{}]", urlString.toString());
			}
			// Connection configuration
			URL url = new URL(urlString.toString());
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(HttpMethod.GET.name());
			conn.setRequestProperty("Connection", "Keep-Alive");

			// Gets the response
			return this.readResponse(restUrl, conn);
		} catch (IOException e) {
			LOGGER.error("Request error", e);
			throw e;
		} catch (Exception e) {
            LOGGER.error("Request error", e);
            throw new IOException(e.getLocalizedMessage());
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	/**
	 * Do a POST HTTP request to the given REST-URL.
	 * 
	 * @param restUrl
	 *            REST URL.
	 * @param params
	 *            Parameters for adding to the query string.
	 * @throws IOException
	 *             if the request go bad.
	 */
	public Object postRequest(String restUrl, Map<String, String> params) throws IOException, WebServiceException {
		return this.postRequest(HttpMethod.POST, restUrl, params);
	}

	/**
	 * Do a PUT HTTP request to the given REST-URL.
	 * 
	 * @param restUrl
	 *            REST URL.
	 * @param params
	 *            Parameters for adding to the query string.
	 * @throws IOException
	 *             if the request go bad.
	 */
	public Object putRequest(String restUrl, Map<String, String> params) throws IOException, WebServiceException {
		return this.postRequest(HttpMethod.PUT, restUrl, params);
	}

	/**
	 * Do a DELETE HTTP request to the given REST-URL.
	 * 
	 * @param restUrl
	 *            REST URL.
	 * @param params
	 *            Parameters for adding to the query string.
	 * @throws IOException
	 *             if the request go bad.
	 */
	public Object deleteRequest(String restUrl, Map<String, String> params) throws IOException, WebServiceException {
		return this.postRequest(HttpMethod.DELETE, restUrl, params);
	}

	/**
	 * Do a POST HTTP request to the given REST-URL.
	 * 
	 * @param httpMethod
	 *            HTTP method for this request (GET, POST, PUT, DELETE).
	 * @param restUrl
	 *            REST URL.
	 * @param params
	 *            Parameters for adding to the query string.
	 * @throws IOException
	 *             if the request go bad.
	 */
	private Object postRequest(HttpMethod httpMethod, String restUrl, Map<String, String> params) throws IOException, WebServiceException {
		HttpURLConnection conn = null;
		BufferedWriter wr = null;
		try {
			// Make the URL
			urlString = new StringBuilder(this.host).append(restUrl);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Doing HTTP request: POST [{}]", urlString.toString());
            }
			// Connection configuration
			URL url = new URL(urlString.toString());
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(HttpMethod.POST.name());
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Connection", "Keep-Alive");

			wr = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			// Writes into the alive connection
			if (httpMethod != HttpMethod.POST) {
				params.put("http_method", httpMethod.name());
			}
			wr.write(this.makeParamsString(params, false));
			wr.flush();
			// Gets the response
			return this.readResponse(restUrl, conn);
		} catch (IOException e) {
			LOGGER.error("Request error", e);
			throw e;
		} catch (Exception e) {
            LOGGER.error("Request error", e);
            throw new IOException(e.getLocalizedMessage());
        } finally {
			if (wr != null) {
				wr.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	private Object readResponse(String restUrl, HttpURLConnection conn) throws IOException, WebServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Connection done. The server's response code is: {}", conn.getResponseCode());
		}
		BufferedReader rd = null;
		try {
			InputStream is = null;
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Reading an OK ({}) response", HttpURLConnection.HTTP_OK);
				}
				is = conn.getInputStream();
            } else if (conn.getResponseCode() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Reading an Error ({}) response", conn.getResponseCode());
                }
                is = conn.getErrorStream();
            } else if (conn.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Reading a Not Found ({}) response", conn.getResponseCode());
                }
                throw new WebServiceException("Page or Resource Not Found", conn.getResponseCode());
			} else if (conn.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Returning a No Content (null) ({}) response", HttpURLConnection.HTTP_NO_CONTENT);
				}
				return null;
			}
			if (is == null) {
			    LOGGER.warn("InputStream is null!!");
			    throw new ConnectException("Can't connect to server");
			}
			rd = new BufferedReader(new InputStreamReader(is));

			StringBuilder response = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
			}
			Object result = this.deserialize(response.toString(), UrlUtils.getInstance().getExtension(restUrl));
			if (result == null) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Nothing was deserialized, returning read content");
                }
                return response.toString();
			} else {
    			if (LOGGER.isDebugEnabled()) {
    				LOGGER.debug("Read object in response is: {}", (result != null ? result.toString() : null));
    			}
    			if (result instanceof Exception) {
    				throw new WebServiceException((Exception) result);
    			}
    			return result;
			}
		} catch (IOException e) {
			LOGGER.error("Request error", e);
			throw e;
		} catch (Exception e) {
            LOGGER.error("Request error", e);
            throw new IOException(e.getLocalizedMessage());
        } finally {
			if (rd != null) {
				rd.close();
			}
		}
	}

	/**
	 * Adds params to a query string. It will encode params' values to not get
	 * errors in the connection.
	 * 
	 * @param params
	 *            Parameters and their values.
	 * @param isGet
	 *            Indicates a GET request. A GET request has to mark the first
	 *            parameter with the '?' symbol. In a POST request it doesn't
	 *            have to do it.
	 * @return a string with the parameters. This string can be appended to a
	 *         query string, or written to an OutputStream.
	 */
	private String makeParamsString(Map<String, String> params, boolean isGet) {
		StringBuilder url = new StringBuilder();
		if (params != null) {
			boolean first = true;
			for (Map.Entry<String, String> entry : params.entrySet()) {
				if (first) {
					if (isGet) {
						url.append("?");
					}
					first = false;
				} else {
					url.append("&");
				}
				try {
					// Hay que codificar los valores de los parametros para que
					// las llamadas REST se hagan correctamente
				    if (entry.getValue() != null) {
				        url.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), CHARSET_ENCODING));
				    } else {
				        LOGGER.warn("Param {} is null", entry.getKey());
                        url.append(entry.getKey()).append("=").append("");
				    }
				} catch (UnsupportedEncodingException ex) {
					// Esta excepcion saltaria si la codificacion no estuviese
					// soportada, pero UTF-8 siempre esta soportada.
					LOGGER.error(ex.getLocalizedMessage());
				}
			}
		}
		return url.toString();
	}

	/**
	 * Deserializes a serialized object that came within the response after a
	 * REST call.
	 * 
	 * @param serializedObject
	 *            Serialized object.
	 * @param extension
	 *            URL's extension (json, 64, xml, etc...).
	 * @return a deserialized object.
	 * @throws IOException
	 *             if object can't be deserialized.
	 */
	private Object deserialize(String serializedObject, String extension) throws IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Deserializing object [{}] to [{}]", serializedObject, extension);
		}
		SerializerFinder finder = new SerializerFinder(extension);
		String serializerClass = finder.findResource(null);
		if (serializerClass == null) {
            LOGGER.warn("Serializer not found for extension [{}]", extension);
		    return null;
		} else {
    		try {
    			if (LOGGER.isDebugEnabled()) {
    				LOGGER.debug("Deserializing using {}", serializerClass);
    			}
    			Class<?> clazz = Class.forName(serializerClass);
    			Method deserializeMethod = clazz.getMethod("deserialize", new Class[] { String.class });
    			if (LOGGER.isDebugEnabled()) {
    				LOGGER.debug("Calling {}.deserialize", serializerClass);
    			}
    			return deserializeMethod.invoke(clazz.newInstance(), serializedObject);
    		} catch (Exception e) {
    			LOGGER.error(e.getLocalizedMessage(), e);
    			LOGGER.error("Can't deserialize object with {} serializer", serializerClass);
    			throw new IOException(e.getLocalizedMessage());
    		}
		}
	}
}
