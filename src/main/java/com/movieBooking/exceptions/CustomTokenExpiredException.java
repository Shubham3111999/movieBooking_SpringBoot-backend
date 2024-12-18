package com.movieBooking.exceptions;

public class CustomTokenExpiredException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomTokenExpiredException(String message) {
        super(message);
    }
}

