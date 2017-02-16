package com.assignment.exceptions;

import java.util.List;

import com.assignment.lucene.dtos.FieldErrorResource;

public class NoCabFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	List<FieldErrorResource> fieldErrors;

	public NoCabFoundException() {
		super("No cabs found in area.");
	}

	public List<FieldErrorResource> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldErrorResource> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}