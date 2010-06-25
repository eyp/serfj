package net.sf.serfj.finders;


import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import net.sf.serfj.Config;

/**
 * Finds a serializer for a request. It finds a class with the the name
 * ExtensionResourceNameSerializer in the directory expected depending on the
 * package style used. Extension could be .xml, .json, .anything, so the finder
 * will look for JsonResourceNameSerializer, AnythingResourceNameSerializer,
 * etc...<br/>
 * <br/>
 * 
 * There are some default serializers:<br/>
 * - net.sf.serfj.serializers.JsonSerializer<br/>
 * - net.sf.serfj.serializers.XmlSerializer<br/>
 * - net.sf.serfj.serializers.PageSerializer<br/>
 * <br/>
 * 
 * that will be used if there aren't any serializers found for a resource.
 * 
 * @author: Eduardo Yáñez 
 * Date: 08-may-2009
 */
public class SerializerFinder extends ResourceFinder {

	protected static final String DEFAULT_SERIALIZERS_PACKAGE = "net.sf.serfj.serializers";
	private static final String PAGE_EXTENSION = "page";
	private static final String JSON_EXTENSION = "json";
	private static final String B64_EXTENSION = "64";
	private static final String XML_EXTENSION = "xml";

	private static Map<String, String> contentType2Extension = new HashMap<String, String>(4);

	public SerializerFinder(String extension) {
		super(null, null, (extension == null ? PAGE_EXTENSION : extension), OFF_OPTION, null);
		this.initExtensionsCache();
	}

	public SerializerFinder(Config config, String extension) {
		super(config.getString(Config.MAIN_PACKAGE), config.getString(Config.ALIAS_SERIALIZERS_PACKAGE), 
		        (extension == null ? PAGE_EXTENSION : extension), config.getString(Config.SUFFIX_SERIALIZER), 
		        config.getString(Config.PACKAGES_STYLE));
        this.initExtensionsCache();
	}

	private void initExtensionsCache() {
        contentType2Extension.put("application/json", "json");
        contentType2Extension.put("text/xml", "xml");
        contentType2Extension.put("application/octect-stream", "64");
	}
	
	/**
	 * Returns a extension from a content-type. If the content-type is not
	 * valid, then a null will be returned.
	 * 
	 * @param contentType
	 *            A content-type. Valid content-types are: - application/json -
	 *            text/xml - application/octect-stream
	 * 
	 * @return a extension (json, 64 or xml).
	 */
	public static String getExtension(String contentType) {
		return contentType2Extension.get(contentType);
	}

	@Override
	protected String defaultResource(String model) {
		String serializer = super.defaultResource(model);
		if (serializer == null && isDefaultImplementation()) {
			serializer = MessageFormat.format("{0}.{1}Serializer", DEFAULT_SERIALIZERS_PACKAGE, 
			        this.getUtils().capitalize(this.getPrefix()));
		}
		return serializer;
	}

	private Boolean isDefaultImplementation() {
		if (JSON_EXTENSION.equals(this.getPrefix().toLowerCase()) 
		        || B64_EXTENSION.equals(this.getPrefix().toLowerCase()) 
		        || XML_EXTENSION.equals(this.getPrefix().toLowerCase())) {
			return true;
		}
		return false;
	}
}
