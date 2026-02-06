package com.specdoctor.global.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.specdoctor.global.error.ErrorCode;
import com.specdoctor.global.error.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	public static final String SERVICE_EXCEPTION_LOG_FORMAT = "ServiceException occurred: {}";
	public static final String UNEXPECTED_ERROR_LOG_FORMAT = "Unexpected error occurred: {}";

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ErrorResponse> handleServiceException(ServiceException e) {
		log.error(SERVICE_EXCEPTION_LOG_FORMAT, e.getMessage(), e);

		ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
		return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorCode().getHttpStatus()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
		log.error(UNEXPECTED_ERROR_LOG_FORMAT, e.getMessage(), e);

		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
