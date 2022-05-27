package com.residencia.comercio.exceptions;

public class HttpMessageNotReadableException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public HttpMessageNotReadableException(String message) {
		super(message);
	}
}