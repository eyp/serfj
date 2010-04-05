package com.elpaso.serfj.client;

/**
 * Exception that encapsulates exceptions thrown by controller's methods.
 * 
 * @author eduardo.yanez
 */
public class WebServiceException extends Exception {

	private static final long serialVersionUID = 5459451496102180297L;

	private int status;
	
	/**
	 * Constructor.
	 */
	public WebServiceException() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param message 
	 *         Exception message.
	 * @param cause 
	 *         An exception.
	 */
	public WebServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param message 
	 *         Exception message.
	 */
	public WebServiceException(String message) {
		super(message);
	}

    /**
     * Constructor.
     * 
     * @param cause
     *         An exception.
     */
    public WebServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     * 
     * @param status
     *         It's usually a HTTP Status Code (404, 500, etc.)
     */
    public WebServiceException(int status) {
        super();
        this.status = status;
    }

    /**
     * Constructor.
     * 
     * @param message 
     *         Exception message.
     * @param cause 
     *         An exception.
     * @param status
     *         It's usually a HTTP Status Code (404, 500, etc.)
     */
    public WebServiceException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }

    /**
     * Constructor.
     * 
     * @param message 
     *         Exception message.
     * @param status
     *         It's usually a HTTP Status Code (404, 500, etc.)
     */
    public WebServiceException(String message, int status) {
        super(message);
        this.status = status;
    }

    /**
     * Constructor.
     * 
     * @param cause
     *         An exception.
     * @param status
     *         It's usually a HTTP Status Code (404, 500, etc.)
     */
    public WebServiceException(Throwable cause, int status) {
        super(cause);
        this.status = status;
    }

    /**
     * Get the exception's status. It's usually a HTTP Status Code (404, 500, etc.)
     */
    public int getStatus() {
        return this.status;
    }
}
