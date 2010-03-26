package com.elpaso.serfj;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elpaso.serfj.annotations.DELETE;
import com.elpaso.serfj.annotations.GET;
import com.elpaso.serfj.annotations.POST;
import com.elpaso.serfj.annotations.PUT;

/**
 * Helper for {@link RestServlet}. It has some methods to know how to invoke
 * actions on controllers.
 * 
 * @author Eduardo Yáñez
 * 
 *         Mar 13, 2010
 */
class ServletHelper {

	/**
	 * Log.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ServletHelper.class);

	/**
	 * Strategy to invoke action on the controller.
	 */
	enum Strategy {
		INHERIT, INTERFACE, SIGNATURE
	};

	/**
	 * Checks if a class method exists.
	 * 
	 * @param clazz
	 *            Class.
	 * @param method
	 *            Method.
	 * @param params
	 *            Method's params.
	 * @return the method, or null if it doesn't exist.
	 */
	Method methodExists(Class<?> clazz, String method, Class<?>[] params) {
		try {
			return clazz.getMethod(method, params);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	/**
	 * Calculates the strategy controller has choice to implement REST actions.
	 * There are 3 different strategies: INHERIT, INTERFACE and SIGNATURE.
	 * 
	 * @param controller
	 *            Controller's class name.
	 * @return The strategy to follow to invoke actions on this controller.
	 * 
	 * @throws ClassNotFoundException
	 *             if controller's class doesn't exist.
	 */
	Strategy calculateStrategy(String controller) throws ClassNotFoundException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Calculating invocation strategy");
		}
		Class<?> clazz = Class.forName(controller);
		if (RestAction.class.isAssignableFrom(clazz)) {
			return Strategy.INHERIT;
		}
		return Strategy.SIGNATURE;
	}

	/**
	 * Invokes URL's action using INHERIT strategy. It means that controller
	 * inherits from {@link RestAction}, so the framework will inject
	 * {@link ResponseHelper} to controller by RestAction.setResposeHelper
	 * method. Furthermore, controller's actions signatures don't have
	 * arguments.
	 * 
	 * 
	 * @param urlInfo
	 *            Information of REST's URL.
	 * @param responseHelper
	 *            ResponseHelper object to inject into the controller.
	 * 
	 * @throws ClassNotFoundException
	 *             if controller's class doesn't exist.
	 * @throws NoSuchMethodException
	 *             if doesn't exist a method for action required in the URL.
	 * @throws IllegalArgumentException
	 *             if controller's method for action has arguments.
	 * @throws IllegalAccessException
	 *             if the controller or its method are not accessibles-
	 * @throws InvocationTargetException
	 *             if the controller's method raise an exception.
	 * @throws InstantiationException
	 *             if it isn't possible to instantiate the controller.
	 */
	Object inheritedStrategy(UrlInfo urlInfo, ResponseHelper responseHelper) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
	        InvocationTargetException, InstantiationException {
		Class<?> clazz = Class.forName(urlInfo.getController());
		Method setResponseHelper = clazz.getMethod("setResponseHelper", new Class<?>[] { ResponseHelper.class });
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Instantiating controller {}", clazz.getCanonicalName());
		}
		Object controllerInstance = clazz.newInstance();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Calling {}.setResponseHelper(ResponseHelper)", clazz.getCanonicalName());
		}
		setResponseHelper.invoke(controllerInstance, responseHelper);
		Method action = clazz.getMethod(urlInfo.getAction(), new Class[] {});
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Calling {}.{}()", urlInfo.getController(), urlInfo.getAction());
		}
		responseHelper.notRenderPage(action);
		return this.invoke(controllerInstance, action, urlInfo);
	}

	/**
	 * Invokes URL's action using SIGNATURE strategy. It means that controller's
	 * method could have these signatures:
	 * 
	 * - action(ResponseHelper, Map<String,Object>). - action(ResponseHelper). -
	 * action(Map<String,Object>). - action().
	 * 
	 * 
	 * @param urlInfo
	 *            Information of REST's URL.
	 * @param responseHelper
	 *            ResponseHelper object to inject into the controller.
	 * 
	 * @throws ClassNotFoundException
	 *             if controller's class doesn't exist.
	 * @throws NoSuchMethodException
	 *             if doesn't exist a method for action required in the URL.
	 * @throws IllegalArgumentException
	 *             if controller's method has different arguments that this
	 *             method tries to pass to it.
	 * @throws IllegalAccessException
	 *             if the controller or its method are not accessibles-
	 * @throws InvocationTargetException
	 *             if the controller's method raise an exception.
	 * @throws InstantiationException
	 *             if it isn't possible to instantiate the controller.
	 */
	Object signatureStrategy(UrlInfo urlInfo, ResponseHelper responseHelper) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException,
	        InstantiationException, NoSuchMethodException {
		Class<?> clazz = Class.forName(urlInfo.getController());
		Object result = null;
		// action(ResponseHelper, Map<String,Object>)
		Method method = this.methodExists(clazz, urlInfo.getAction(), new Class[] { ResponseHelper.class, Map.class });
		if (method != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Calling {}.{}(ResponseHelper, Map<String,Object>)", urlInfo.getController(), urlInfo.getAction());
			}
			responseHelper.notRenderPage(method);
			result = this.invoke(clazz.newInstance(), method, urlInfo, responseHelper, responseHelper.getParams());
		} else {
			// action(ResponseHelper)
			method = this.methodExists(clazz, urlInfo.getAction(), new Class[] { ResponseHelper.class });
			if (method != null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Calling {}.{}(ResponseHelper)", urlInfo.getController(), urlInfo.getAction());
				}
				responseHelper.notRenderPage(method);
				result = this.invoke(clazz.newInstance(), method, urlInfo, responseHelper);
			} else {
				// action(Map<String,Object>)
				method = this.methodExists(clazz, urlInfo.getAction(), new Class[] { Map.class });
				if (method != null) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Calling {}.{}(Map<String,Object>)", urlInfo.getController(), urlInfo.getAction());
					}
					responseHelper.notRenderPage(method);
					result = this.invoke(clazz.newInstance(), method, urlInfo, responseHelper.getParams());
				} else {
					// action()
					method = clazz.getMethod(urlInfo.getAction(), new Class[] {});
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Calling {}.{}()", urlInfo.getController(), urlInfo.getAction());
					}
					responseHelper.notRenderPage(method);
					result = this.invoke(clazz.newInstance(), method, urlInfo);
				}
			}
		}
		return result;
	}

	/**
	 * Invokes a method checking previously if that method accepts requests
	 * using a particular HTTP_METHOD.
	 * 
	 * @param clazz
	 *            A class.
	 * @param method
	 *            A method to invoke.
	 * @param urlInfo
	 *            URL information extracted by the framework.
	 * @param args
	 *            Arguments for the method which will be invoked.
	 * @return the object returned by the method onvoked, or null.
	 * @throws IllegalArgumentException
	 *             if the HTTP_METHOD that comes in the request is not accepted
	 *             by class's method.
	 */
	private Object invoke(Object clazz, Method method, UrlInfo urlInfo, Object... args) throws IllegalAccessException, InvocationTargetException {
		if (this.isRequestMethodServed(method, urlInfo.getRequestMethod())) {
			return method.invoke(clazz, args);
		} else {
			throw new IllegalArgumentException("Method " + urlInfo.getController() + "." + urlInfo.getAction() + " doesn't accept requests by " + urlInfo.getRequestMethod() + " HTTP_METHOD");
		}
	}

	/**
	 * Checks if a resource's method attends HTTP requests using a concrete
	 * HTTP_METHOD (GET, POST, PUT, DELETE). A method accept a particular
	 * HTTP_METHOD if it's annotated with the correct annotation (@GET, @POST,
	 * @PUT, @DELETE).
	 * 
	 * @param method
	 *            A class's method.
	 * @param httpMethod
	 *            HTTP_METHOD that comes in the request.
	 * @return <code>true</code> if the method accepts that HTTP_METHOD,
	 *         <code>false</code> otherwise.
	 * 
	 * @throws IllegalArgumentException
	 *             if HttpMethod is not supported.
	 */
	private boolean isRequestMethodServed(Method method, HttpMethod httpMethod) {
		boolean accepts = false;
		switch (httpMethod) {
		case GET:
			accepts = method.getAnnotation(GET.class) != null;
			break;
		case POST:
			accepts = method.getAnnotation(POST.class) != null;
			break;
		case PUT:
			accepts = method.getAnnotation(PUT.class) != null;
			break;
		case DELETE:
			accepts = method.getAnnotation(DELETE.class) != null;
			break;
		default:
			throw new IllegalArgumentException("HTTP method not supported: " + httpMethod);
		}
		return accepts;
	}
}
