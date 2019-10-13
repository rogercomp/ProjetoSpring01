package com.iftm.prjreferencia.services.exceptions;

public class JWTAuthorizationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public JWTAuthorizationException(String msg) {
		super(msg);
	}
}
