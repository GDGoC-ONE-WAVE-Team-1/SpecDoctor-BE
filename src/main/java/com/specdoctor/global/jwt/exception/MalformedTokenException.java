package com.specdoctor.global.jwt.exception;

import com.specdoctor.global.error.ErrorCode;
import com.specdoctor.global.error.exception.ServiceException;

public class MalformedTokenException extends ServiceException {
	public MalformedTokenException() {
		super(ErrorCode.MALFORMED_TOKEN);
	}
}
