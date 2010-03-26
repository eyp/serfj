package com.elpaso.serfj.client;

/**
 * Exception that encapsulates exceptions thrown by controller's methods.
 * 
 * @author eduardo.yanez
 */
public class WebServiceException extends Exception {

	private static final long serialVersionUID = 5459451496102180297L;

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
	 * @param cause
	 */
	public WebServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public WebServiceException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 */
	public WebServiceException(Throwable cause) {
		super(cause);
	}
}
