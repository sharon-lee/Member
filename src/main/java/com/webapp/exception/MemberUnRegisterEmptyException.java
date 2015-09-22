package com.webapp.exception;

public class MemberUnRegisterEmptyException extends RuntimeException {

	/** 가독성을 위해 Exception Class 
	 * Serialization 
	 */
	private static final long serialVersionUID = 1L;

	public MemberUnRegisterEmptyException() {
		super();
	}
	
	//Overloading
	public MemberUnRegisterEmptyException(String message) {
		super(message);
	}
	
	//Refactoring
	public MemberUnRegisterEmptyException(Throwable cause) {
		super(cause);
	}
	
	public MemberUnRegisterEmptyException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
