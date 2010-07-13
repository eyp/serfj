package net.sf.serfj;

import net.sf.serfj.config.ConfigFileIOException;
import net.sf.serfj.config.ConfigParam;
import net.sf.serfj.config.SystemConfig;

/**
 * Framework configuration.
 * 
 * @author Eduardo Yáñez
 */
public class Config extends SystemConfig {

	/**
	 * Flag for debug purposes.
	 */
	protected static final ConfigParam DEBUG = new ConfigParam("debug");

	/**
	 * Java main package where the source is located.
	 */
	public static final ConfigParam MAIN_PACKAGE = new ConfigParam("main.package");

	/**
	 * Packages style.
	 */
	public static final ConfigParam PACKAGES_STYLE = new ConfigParam("packages.style");

	/**
	 * Java package where controllers will be located.
	 */
	public static final ConfigParam ALIAS_CONTROLLERS_PACKAGE = new ConfigParam("alias.controllers.package", "controllers");

	/**
	 * Java package where serializers for responses will be located.
	 */
	public static final ConfigParam ALIAS_SERIALIZERS_PACKAGE = new ConfigParam("alias.serializers.package", "serializers");

	/**
	 * Suffix used for controller classes
	 */
	public static final ConfigParam SUFFIX_CONTROLLER = new ConfigParam("suffix.controllers", "OFF");

	/**
	 * Suffix used for serializer classes
	 */
	public static final ConfigParam SUFFIX_SERIALIZER = new ConfigParam("suffix.serializer", "Serializer");

	/**
	 * Directory where the views will be located.
	 */
	public static final ConfigParam VIEWS_DIRECTORY = new ConfigParam("views.directory", "views");

	public Config(String filename) throws ConfigFileIOException {
		super(filename);
	}
}
