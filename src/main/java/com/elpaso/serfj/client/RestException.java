package com.elpaso.serfj.client;

/**
 * Exception that encapsulates exceptions thrown by controller's methods.
 * 
 * @author eduardo.yanez
 */
public class RestException extends Exception {

    private static final long serialVersionUID = 5459451496102180297L;

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
