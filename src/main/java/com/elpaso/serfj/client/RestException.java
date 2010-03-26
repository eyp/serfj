package com.elpaso.serfj.client;

/**
 * Exception that encapsulates exceptions thrown by controller's methods.
 * 
 * @author eduardo.yanez
 */
public class RestException extends Exception {

    /**
     * Constructor. 
     */
    public RestException() {
        super();
    }

    /**
     * Constructor.
     *  
     * @param message
     * @param cause
     */
    public RestException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *  
     * @param message
     */
    public RestException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *  
     * @param cause
     */
    public RestException(Throwable cause) {
        super(cause);
    }
}
