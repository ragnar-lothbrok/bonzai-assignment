package com.assignment.exceptions;

import java.util.List;

import com.assignment.lucene.dtos.FieldErrorResource;

public class IndexWriteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	List<FieldErrorResource> fieldErrors;

	public IndexWriteException() {
		super("Failed to write in Index writer.");
	}

	public List<FieldErrorResource> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldErrorResource> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}