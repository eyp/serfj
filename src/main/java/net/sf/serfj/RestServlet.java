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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.serfj.config.ConfigFileIOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class. This servlet dispatches REST requests to controllers. It parses
 * the URL, extracting resources and resources' Ids so developer will be able to
 * get them in the method's controller.
 * 
 * @author Eduardo Yáñez
 * 
 */
public class RestServlet extends HttpServlet {

	private static final long serialVersionUID = 9209683558191927011L;

	/**
	 * Log.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RestServlet.class);

	/**
	 * Parameter for supply the HTTP request method that doesn't support the
	 * browsers (PUT and DELETE), but could be set to GET and POST too.
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
			LOGGER.error("Can't load framework configuration", e);
			throw new ServletException(e);
		}
		urlInspector = new UrlInspector(config);
	}

	/**
	 * Parses the request to get information about what controller is trying to call, then
	 * invoke the action from that controller (if any), and finally gives an answer.<br>
	 * <br>
	 * Basically it only dispatchs the request to a controller.
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Eliminamos de la URI el contexto
		String url = request.getRequestURI().substring(request.getContextPath().length());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("url => {}", url);
			LOGGER.debug("HTTP_METHOD => {}", request.getMethod());
			LOGGER.debug("queryString => {}", request.getQueryString());
			LOGGER.debug("Context [{}]", request.getContextPath());
		}

		HttpMethod requestMethod = HttpMethod.valueOf(request.getMethod());
		if (requestMethod == HttpMethod.POST) {
			String httpMethodParam = request.getParameter(HTTP_METHOD_PARAM);
			LOGGER.debug("param: http_method => {}", httpMethodParam);
			if (httpMethodParam != null) {
				requestMethod = HttpMethod.valueOf(httpMethodParam);
			}
		}
		// Getting all the information from the URL
		UrlInfo urlInfo = urlInspector.getUrlInfo(url, requestMethod);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("URL info {}", urlInfo.toString());
		}
		// Calling the controller's action
		ResponseHelper responseHelper = new ResponseHelper(this.getServletContext(), request, response, urlInfo, config.getString(Config.VIEWS_DIRECTORY));
		helper.invokeAction(urlInfo, responseHelper);
		responseHelper.doResponse();
	}
}
