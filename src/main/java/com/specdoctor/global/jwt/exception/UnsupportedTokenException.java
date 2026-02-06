package com.specdoctor.global.jwt.exception;

import com.specdoctor.global.error.ErrorCode;
import com.specdoctor.global.error.exception.ServiceException;

public class UnsupportedTokenException extends ServiceException {
	public UnsupportedTokenException() {
		super(ErrorCode.UNSUPPORTED_TOKEN);
	}
}
