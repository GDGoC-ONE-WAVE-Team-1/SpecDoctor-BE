package com.specdoctor.global.jwt.exception;

import com.specdoctor.global.error.ErrorCode;
import com.specdoctor.global.error.exception.ServiceException;

public class InvalidTokenException extends ServiceException {
	public InvalidTokenException() {
		super(ErrorCode.INVALID_TOKEN);
	}
}
