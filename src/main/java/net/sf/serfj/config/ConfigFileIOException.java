package net.sf.serfj.config;

/**
 * Configuration file not found or can't read it.
 * 
 * @author eduardo.yanez
 */
public class ConfigFileIOException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 9197319082561488840L;

    /**
     * Constructor with a message.
     * 
     * @param msg - Message error.
     */
    public ConfigFileIOException(String msg) {
        super(msg);
    }
}
