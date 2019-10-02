package com.iftm.prjreferencia.resources.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String fieldName;
	private String message;
	
	public FieldMessage() {
		
	}

	public FieldMessage(String fieldName, String message) {		
		this.fieldName = fieldName;
		this.message = message;
	}

	public String getFieldMessage() {
		return fieldName;
	}

	public void setFieldMessage(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
}
