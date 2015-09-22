package com.webapp.exception;

public class AlreadyExistingMemberException extends RuntimeException {

	/** 가독성을 위해 Exception Class 
	 * Serialization 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyExistingMemberException() {
		super();
	}
	
	//Overloading
	public AlreadyExistingMemberException(String message) {
		super(message);
	}
	
	//Refactoring
	public AlreadyExistingMemberException(Throwable cause) {
		super(cause);
	}
	
	public AlreadyExistingMemberException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
