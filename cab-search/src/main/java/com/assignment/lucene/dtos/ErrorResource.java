package com.assignment.lucene.dtos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResource {

	private String code;
	private String message;

	List<FieldErrorResource> fieldErros = new ArrayList<FieldErrorResource>();

	public ErrorResource(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<FieldErrorResource> getFieldErros() {
		return fieldErros;
	}

	public void setFieldErros(List<FieldErrorResource> fieldErros) {
		this.fieldErros = fieldErros;
	}

}