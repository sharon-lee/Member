package com.webapp.exception;

public class IdPasswordNotMatchException extends RuntimeException {

	/** 가독성을 위해 Exception Class 
	 * Serialization 
	 */
	private static final long serialVersionUID = 1L;

	public IdPasswordNotMatchException() {
		super();
	}
	
	//Overloading
	public IdPasswordNotMatchException(String message) {
		super(message);
	}
	
	//Refactoring
	public IdPasswordNotMatchException(Throwable cause) {
		super(cause);
	}
	
	public IdPasswordNotMatchException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
