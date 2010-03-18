package com.elpaso.serfj.finders;

import com.elpaso.serfj.Config;

/**
 * @author: Eduardo Yáñez
 * Date: 08-may-2009
 */
public class ControllerFinder extends ResourceFinder {

    public ControllerFinder(Config config) {
        super(config.getString(Config.MAIN_PACKAGE), config.getString(Config.ALIAS_CONTROLLERS_PACKAGE),
                config.getString(Config.SUFFIX_CONTROLLER), config.getString(Config.PACKAGES_STYLE));
    }
}
