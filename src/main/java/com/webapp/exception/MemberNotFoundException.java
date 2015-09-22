package com.webapp.exception;

public class MemberNotFoundException extends RuntimeException {

	/** 가독성을 위해 Exception Class 
	 * Serialization 
	 */
	private static final long serialVersionUID = 1L;

	public MemberNotFoundException() {
		super();
	}
	
	//Overloading
	public MemberNotFoundException(String message) {
		super(message);
	}
	
	//Refactoring
	public MemberNotFoundException(Throwable cause) {
		super(cause);
	}
	
	public MemberNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
