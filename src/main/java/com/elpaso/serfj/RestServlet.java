package com.elpaso.serfj;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elpaso.config.ConfigFileIOException;

/**
 * Clase principal. Este servlet hace de dispatcher, es decir, recibe una URL
 * tipo REST, y redirige la petición al controlador correspondiente, pasándole
 * si es necesario los identificadores que vengan en la petición.
 * 
 * @author Eduardo Yáñez
 *
 */
public class RestServlet extends HttpServlet {

	private static final long serialVersionUID = 9209683558191927011L;

	/**
     * Log.
     */
    private static final Logger logger = LoggerFactory.getLogger(RestServlet.class);

    /**
     * Parameter for supply the HTTP request method that doesn't support the browsers (PUT and DELETE),
     * but could be set to GET and POST too.
     */
    private static final String HTTP_METHOD_PARAM = "http_method";

    /**
     * Configuration.
     */
    private Config config;

    /**
     * URL manager.
     */
    private UrlInspector urlInspector;

    private ServletHelper helper = new ServletHelper();
    
    /**
     * Reads configuration from /serfj.properties.
     *
     * @throws javax.servlet.ServletException
     */
	@Override
	public void init() throws ServletException {
		super.init();
        try {
            config = new Config("/config/serfj.properties");
        } catch (ConfigFileIOException e) {
            logger.error("Can't load framework configuration", e);
            throw new ServletException(e);
        }
        urlInspector = new UrlInspector(config);
	}

    /**
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Eliminamos de la URI el contexto
        String url = request.getRequestURI().substring(request.getContextPath().length());
        if (logger.isDebugEnabled()) {
            logger.debug("url => {}", url);
            logger.debug("HTTP_METHOD => {}", request.getMethod());
            logger.debug("queryString => {}", request.getQueryString());
            logger.debug("Context [{}]", request.getContextPath());
        }
        
        HttpMethod requestMethod = HttpMethod.valueOf(request.getMethod());
        if (requestMethod == HttpMethod.POST) {
            String httpMethodParam = request.getParameter(HTTP_METHOD_PARAM);
            if (logger.isDebugEnabled()) {
                logger.debug("param: http_method => {}", httpMethodParam);
            }
            if (httpMethodParam != null) {
                requestMethod = HttpMethod.valueOf(httpMethodParam);
            }
        }
        // Getting all the information from the URL
        UrlInfo urlInfo = urlInspector.getUrlInfo(url, requestMethod);
        if (logger.isDebugEnabled()) {
            logger.debug("URL info {}", urlInfo.toString());
        }
        // Calling the controller's action
        ResponseHelper responseHelper = new ResponseHelper(this.getServletContext(), request, response, 
                urlInfo, config.getString(Config.VIEWS_DIRECTORY));
        invokeAction(urlInfo, responseHelper);
        responseHelper.doResponse();
    }

    private void invokeAction(UrlInfo urlInfo, ResponseHelper responseHelper)
            throws ServletException {
        try {
            // May be there isn't any controller, so the page will be rendered without calling any action
            if (urlInfo.getController() != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Calculating invocation strategy");
                }
            	   ServletHelper.Strategy strategy = helper.calculateStrategy(urlInfo.getController());
                if (logger.isDebugEnabled()) {
                    logger.debug("Strategy: {}", strategy);
                }
                switch (strategy) {
                case INHERIT: 
                    helper.inheritedStrategy(urlInfo, responseHelper);
                	    break;
                default:
                	    helper.signatureStrategy(urlInfo, responseHelper);
                	    break;
                }
            } else {
                if (logger.isWarnEnabled()) {
                    logger.warn("There is not controller defined for url [{}]", urlInfo.getUrl());
                }
            }
        } catch (ClassNotFoundException e) {
            logger.warn(e.getLocalizedMessage(), e);
        } catch (IllegalArgumentException e) {
            logger.warn(e.getLocalizedMessage(), e); 
            throw new ServletException(e);
        } catch (InvocationTargetException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new ServletException(e);
        } catch (IllegalAccessException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new ServletException(e);
        } catch (NoSuchMethodException e) {
            logger.warn("NoSuchMethodException {}", e.getLocalizedMessage());
        } catch (InstantiationException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new ServletException(e);
        }
    }
    
}
