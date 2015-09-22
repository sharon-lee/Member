package com.webapp.exception;

public class MemberModifyEmptyException extends RuntimeException {

	/** 가독성을 위해 Exception Class 
	 * Serialization 
	 */
	private static final long serialVersionUID = 1L;

	public MemberModifyEmptyException() {
		super();
	}
	
	//Overloading
	public MemberModifyEmptyException(String message) {
		super(message);
	}
	
	//Refactoring
	public MemberModifyEmptyException(Throwable cause) {
		super(cause);
	}
	
	public MemberModifyEmptyException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
