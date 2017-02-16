package com.assignment.exceptions;

import java.util.List;

import com.assignment.lucene.dtos.FieldErrorResource;

public class LatLongRangeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	List<FieldErrorResource> fieldErrors;

	public LatLongRangeException() {
		super("Invalid Lat Long");
	}
	
	public LatLongRangeException(String message) {
		super(message);
	}

	public List<FieldErrorResource> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldErrorResource> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}