package com.elpaso.serfj.finders;

import com.elpaso.serfj.Config;

import java.text.MessageFormat;

/**
 * Finds a serializer for a request. It finds a class with the the name
 * ExtensionResourceNameSerializer in the directory expected depending on the package style used.
 * Extension could be .xml, .json, .anything, so the finder will look for JsonResourceNameSerializer,
 * AnythingResourceNameSerializer, etc...<br/><br/>
 *
 * There are some default serializers:<br/>
 * - com.elpaso.serfj.serializers.JsonSerializer<br/>
 * - com.elpaso.serfj.serializers.XmlSerializer<br/>
 * - com.elpaso.serfj.serializers.PageSerializer<br/><br/>
 *
 * that will be used if there aren't any serializers found for a resource.
 * 
 * @author: Eduardo Yáñez
 * Date: 08-may-2009
 */
public class SerializerFinder extends ResourceFinder {

    protected static final String DEFAULT_SERIALIZERS_PACKAGE = "com.elpaso.serfj.serializers";
    private static final String PAGE_EXTENSION = "page";
    private static final String JSON_EXTENSION = "json";
    private static final String XML_EXTENSION = "xml";

    public SerializerFinder(Config config, String extension) {
        super(config.getString(Config.MAIN_PACKAGE), config.getString(Config.ALIAS_HELPERS_PACKAGE),
                (extension == null ? PAGE_EXTENSION : extension),
                config.getString(Config.SUFFIX_SERIALIZER), config.getString(Config.PACKAGES_STYLE));
    }

    @Override
    protected String defaultResource(String model) {
        String serializer = super.defaultResource(model);
        if (serializer == null && isDefaultImplementation()) {
            serializer = MessageFormat.format("{0}.{1}Serializer", DEFAULT_SERIALIZERS_PACKAGE,
                    utils.capitalize(this.prefix));
        }
        return serializer;
    }

    private Boolean isDefaultImplementation() {
        if (JSON_EXTENSION.equals(this.prefix.toLowerCase())
            || XML_EXTENSION.equals(this.prefix.toLowerCase())) {
            return true;
        }
        return false;
    }
}
