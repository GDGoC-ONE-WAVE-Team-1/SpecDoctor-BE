package com.specdoctor.global.jwt.exception;

import com.specdoctor.global.error.ErrorCode;
import com.specdoctor.global.error.exception.ServiceException;

public class ExpiredTokenException extends ServiceException {
	public ExpiredTokenException() {
		super(ErrorCode.EXPIRED_TOKEN);
	}
}
