package com.assignment.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.assignment.lucene.dtos.ErrorResource;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ LatLongRangeException.class })
	protected ResponseEntity<Object> handleLatLongRangeExceptionRequest(RuntimeException e, WebRequest request) {
		LatLongRangeException ire = (LatLongRangeException) e;
		ErrorResource error = new ErrorResource("LAT_LONG_ERROR", e.getMessage());
		error.setFieldErros(ire.getFieldErrors());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(e, error, headers, HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ IndexWriteException.class })
	protected ResponseEntity<Object> handleIndexWriteExceptionRequest(RuntimeException e, WebRequest request) {
		IndexWriteException ire = (IndexWriteException) e;
		ErrorResource error = new ErrorResource("INDEX_EXCEPTION", "Failed to write in Index writer.");
		error.setFieldErros(ire.getFieldErrors());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(e, error, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ NoCabFoundException.class })
	protected ResponseEntity<Object> handleNotFoundRequest(RuntimeException e, WebRequest request) {
		NoCabFoundException ire = (NoCabFoundException) e;
		ErrorResource error = new ErrorResource("NO_CAB_FOUND", "No Cab found in area.");
		error.setFieldErros(ire.getFieldErrors());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(e, error, headers, HttpStatus.BAD_REQUEST, request);
	}
}
