package com.elpaso.serfj.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elpaso.config.ConfigFileIOException;
import com.elpaso.serfj.Config;
import com.elpaso.serfj.HttpMethod;
import com.elpaso.serfj.finders.SerializerFinder;
import com.elpaso.serfj.util.UrlUtils;

/**
 * Client to manage REST calls to a REST Controller. It provides methods to make POST, GET, PUT and DELETE
 * HTTP request to any controller. It's necessary to provide the resource's name (plural) and the action if anyone
 * wants to be invocated.
 * 
 * @author eduardo.yanez
 */
public class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    private static final String CHARSET_ENCODING = "UTF-8";

    private String host;

    private StringBuilder urlString = new StringBuilder();

    private Config config;
    
    /**
     * Constructor. This constructor has to be used in case the server is in the same context as the client.
     */
    public Client() {
        this("/");
    }

    /**
     * Constructor.
     * 
     * @param host - Host which will receive the calls.
     */
    public Client(String host) {
        super();
        this.host = host;
        // Si no termina en '/', hacemos que termine en '/'.
        if (host.lastIndexOf('/') != host.length() - 1 && host.length() != 1) {
            this.host += "/";
        }
        try {
            config = new Config("/config/serfj.properties");
        } catch (ConfigFileIOException e) {
            LOGGER.error("Can't load framework configuration", e);
        }
    }

    /**
     * Do a GET HTTP request to the given REST-URL.
     * 
     * @param restUrl - REST URL.
     * @param params - Parameters for adding to the query string.
     * @throws IOException if the request go bad.
     */
    public Object getRequest(String restUrl, Map<String, String> params) throws IOException {
        HttpURLConnection conn = null;
        try {
            // Make the URL
            urlString = new StringBuilder(this.host).append(restUrl);
            urlString.append(this.makeParamsString(params));
            LOGGER.debug("Doing HTTP request: GET [{}]", urlString.toString());
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
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * Do a POST HTTP request to the given REST-URL.
     * 
     * @param restUrl - REST URL.
     * @param params - Parameters for adding to the query string.
     * @throws IOException if the request go bad.
     */
    public void postRequest(String restUrl, Map<String, String> params) throws IOException {
        this.postRequest(HttpMethod.POST, restUrl, params);
    }

    /**
     * Do a PUT HTTP request to the given REST-URL.
     * 
     * @param restUrl - REST URL.
     * @param params - Parameters for adding to the query string.
     * @throws IOException if the request go bad.
     */
    public void putRequest(String restUrl, Map<String, String> params) throws IOException {
        this.postRequest(HttpMethod.PUT, restUrl, params);
    }

    /**
     * Do a DELETE HTTP request to the given REST-URL.
     * 
     * @param restUrl - REST URL.
     * @param params - Parameters for adding to the query string.
     * @throws IOException if the request go bad.
     */
    public void deleteRequest(String restUrl, Map<String, String> params) throws IOException {
        this.postRequest(HttpMethod.DELETE, restUrl, params);
    }

    /**
     * Do a POST HTTP request to the given REST-URL.
     * 
     * @param httpMethod - HTTP method for this request (GET, POST, PUT, DELETE).
     * @param restUrl - REST URL.
     * @param params - Parameters for adding to the query string.
     * @throws IOException if the request go bad.
     */
    private Object postRequest(HttpMethod httpMethod, String restUrl, Map<String, String> params) throws IOException {
        HttpURLConnection conn = null;
        BufferedWriter wr = null;
        try {
            // Make the URL
            urlString = new StringBuilder(this.host).append(restUrl);
            LOGGER.debug("Doing HTTP request: POST [{}]", urlString.toString());
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
            wr.write(this.makeParamsString(params));
            wr.flush();
            wr.close();
            wr = null;
            // Gets the response
            return this.readResponse(restUrl, conn);
        } catch (IOException e) {
            LOGGER.error("Request error", e);
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private Object readResponse(String restUrl, HttpURLConnection conn) throws IOException {
        LOGGER.debug("Connection done. The server's response code is: {}", conn.getResponseCode());
        BufferedReader rd = null;
        try {
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                }
                return this.deserialize(response.toString(), UrlUtils.getInstance().getExtension(restUrl));
            } else {
                return null;
            }
        } catch (IOException e) {
            LOGGER.error("Request error", e);
            throw e;
        } finally {
            if (rd != null) {
                rd.close();
            }
        }
    }
    
    /**
     * Adds params to a query string. It will encode params' values to not get errors in the connection.
     * 
     * @param params - Parameters and their values.
     * 
     * @return a string with the parameters. This string can be appended to a query string, or written to an
     * {@link OutputStream}.
     */
    private String makeParamsString(Map<String, String> params) {
        StringBuilder url = new StringBuilder();
        if (params != null) {
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first) {
                    url.append("?");
                    first = false;
                } else {
                    url.append("&");
                }
                try {
                    // Hay que codificar los valores de los parametros para que las llamadas REST se hagan
                    // correctamente
                    url.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), CHARSET_ENCODING));
                } catch (UnsupportedEncodingException ex) {
                    // Esta excepcion saltaria si la codificacion no estuviese soportada, pero UTF-8 siempre esta soportada.
                    LOGGER.error(ex.getLocalizedMessage());
                }
            }
        }
        return url.toString();
    }
    
    /**
     * Deserializes a serialized object that came within the response after a REST call.
     * 
     * @param serializedObject - Serialized object.
     * @param contentType - Content type used for serialization.
     * @return a deserialized object.
     * 
     * @throws IOException if object can't be deserialized.
     */
    private Object deserialize(String serializedObject, String contentType) throws IOException {
        SerializerFinder finder = new SerializerFinder(config, SerializerFinder.getExtension(contentType));
        String serializerClass = finder.findResource(null);
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Serializing using {}", serializerClass);
            }
            Class<?> clazz = Class.forName(serializerClass);
            Method deserializeMethod = clazz.getMethod("deserialize", new Class[] {Serializable.class});
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Calling {}.serialize", serializerClass);
            }
            return deserializeMethod.invoke(clazz.newInstance(), serializedObject);
        } catch (Exception e) {
            LOGGER.error("Can't deserialize object with {} serializer: {}", serializerClass, e.getLocalizedMessage());
            throw new IOException(e.getLocalizedMessage());
        }
    }
}
