package com.lms.exception;

public class BookNotBorrowedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BookNotBorrowedException(String message) {
		 super(message);
	}

}
