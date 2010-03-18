package com.elpaso.serfj;

import com.elpaso.serfj.finders.ControllerFinder;
import com.elpaso.serfj.finders.SerializerFinder;
import com.elpaso.serfj.util.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Eduardo Yáñez
 *         Date: 01-may-2009
 */
public class UrlInspector {
    /**
     * Log.
     */
    private static final Logger logger = LoggerFactory.getLogger(UrlInspector.class);

    // Standar actions.
    private static final String INDEX_ACTION = "index";
    private static final String SHOW_ACTION = "show";
    private static final String EDIT_ACTION = "edit";
    private static final String CREATE_ACTION = "create";
    private static final String UPDATE_ACTION = "update";
    private static final String DESTROY_ACTION = "delete";
    protected static final String NEW_ACTION = "new";
    protected static final String NEW_METHOD = "newResource";

    private UrlUtils utils;
    private Config config;

    public UrlInspector(Config config) {
        this.config = config;
        this.utils = UrlUtils.getInstance();
    }

    /**
     * Gets the information which comes implicit in the URL, that is, the identifiers,
     * resources and the REST action.
     * <p/>
     * Examples of several URL formats:
     * <p/>
     * /orders
     * /orders/1
     * /orders/new
     * /orders/1/edit
     * /orders/1/some-action
     * /orders/1/items
     * /orders/1/items/some-action
     * /orders/1/items/new
     * /orders/1/items/2
     * /orders/1/items/2/edit
     *
     * @param url - Url to process.
     * @param requestMethod - HTTP request method (GET, POST, PUT, DELETE)
     * @return an object with all the information related with the URL.
     */
    public UrlInfo getUrlInfo(String url, HttpMethod requestMethod) {
        UrlInfo info = new UrlInfo(url, requestMethod);
        // Split URL by slash
        String[] splits = url.split("/");
        String resource = null;
        String id = null;
        String action = null;
        Integer lastElement = splits.length - 1;
        // Reverse loop to start for the resource or action
        for (int i = lastElement; i > 0; i--) {
            String split = utils.cleanString(splits[i]);
            if (resource == null && isResource(split)) {
                resource = utils.singularize(split);
                info.setController(getControllerClass(split));
            } else if (action == null && !utils.isIdentifier(split) && i == lastElement) {
                action = split;
            } else if (utils.isIdentifier(split)) {
                if (this.isMainId(id, resource, splits[lastElement])) {
                    id = split;
                } else {
                    info.addId(utils.singularize(splits[i - 1]), split);
                }
            }
        }
        // Puts the main resource's ID
        info.addId(id);
        // Puts the main resource
        info.setResource(resource);
        // Puts the REST action
        info.setAction(defineAction(id, action, requestMethod));
        // Puts the result type
        info.setSerializer(this.getSerializerClass(resource, utils.removeQueryString(splits[lastElement])));
        info.setExtension(this.getExtension(utils.removeQueryString(splits[lastElement])));
        return info;
    }

    /**
     * Checks if is the turn for the main id, or it is a secondary id.
     *
     * @param id - Current id.
     * @param resource - Current resource.
     * @param lastElement - Last element of the URL.
     * @return true if is the main id, false otherwise.
     */
    private Boolean isMainId(String id, String resource, String lastElement) {
        Boolean isMainId = false;
        if (id == null) {
            if (!utils.singularize(utils.cleanString(lastElement)).equals(resource)) {
                if (resource == null) {
                    isMainId = true;
                }
            }
        }
        return isMainId;
    }

    private String defineAction(String id, String urlAction, HttpMethod requestMethod) {
        String action = null;
        if (urlAction == null) {
            if (requestMethod == HttpMethod.GET) {
                if (id == null) {
                    action = INDEX_ACTION;
                } else {
                    action = SHOW_ACTION;
                }
            } else if (requestMethod == HttpMethod.POST) {
                action = CREATE_ACTION;
            } else if (requestMethod == HttpMethod.DELETE) {
                action = DESTROY_ACTION;
            } else if (requestMethod.equals(HttpMethod.PUT)) {
                action = UPDATE_ACTION;
            }
        } else {
            if (requestMethod == HttpMethod.GET) {
                if (id == null && urlAction.equals(NEW_ACTION)) {
                    action = NEW_METHOD;
                } else if (id != null && urlAction.equals(EDIT_ACTION)) {
                    action = EDIT_ACTION;
                } else {
                   action = urlAction;
                }
            } else {
                action = urlAction;
            }
        }
        return action;
    }

    /**
     * Checks if a string represents a resource or not. If there is a class which could be
     * instantiated then is a resource, elsewhere it isn't
     *
     * @param resource - String that coulb be a resource.
     * @return true if chunk is a resource.
     */
    Boolean isResource(String resource) {
        Boolean isResource = false;
        if (!utils.isIdentifier(resource)) {
            try {
                String clazz = getControllerClass(resource);
                if (clazz != null) {
                    Class.forName(clazz);
                    isResource = true;
                }
            } catch (ClassNotFoundException e) {
                // It isn't a resource because there isn't a controller for it
            }
        }
        return isResource;
    }

    /**
     * Gets controller class for a resource.
     *
     * @param resource - Resource that will managed by the controller class.
     * @return The fully qualified name of the controller class for this resource and extension.
     */
    String getControllerClass(String resource) {
        ControllerFinder finder = new ControllerFinder(config);
        String controllerClass = finder.findResource(resource);
        if (logger.isDebugEnabled()) {
            logger.debug("Controller class: {}", controllerClass);
        }
        return controllerClass;
    }

    /**
     * Gets serializer class for a resource. If the last part of the URL has an extension,
     * then could be a Serializer class to render the result in a specific way. If the extension
     * is .xml it hopes that a ResourceNameXmlSerializer exists to render the resource as Xml.
     * The extension can be anything.
     *
     * @param resource - Resource that will managed by the controller class.
     * @param urlLastElement - Last element of the URL analyzed.
     * @return The fully qualified name of the serializer class for this resource and extension.
     */
    protected String getSerializerClass(String resource, String urlLastElement) {
        String serializerClass = null;
        String extension = this.getExtension(urlLastElement);
        if (extension != null) {
            SerializerFinder finder = new SerializerFinder(config, extension);
            serializerClass = finder.findResource(resource);
        }
        return serializerClass;
    }

    /**
     * Gets the extension used in the URL, if any.
     *
     * @param urlLastElement - Last element of the URL analyzed.
     * @return an extension, or null if there isn't any.
     */
    private String getExtension(String urlLastElement) {
        int dotIndex = urlLastElement.lastIndexOf(".");
        String extension = null;
        if (dotIndex > 0) {
            extension = urlLastElement.substring(dotIndex + 1);
        }
        return extension;
    }
}
