package com.jp.testing.exception;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4536564160484109087L;

	public ResourceNotFoundException(String msg) {
		super(msg);
	}

}
