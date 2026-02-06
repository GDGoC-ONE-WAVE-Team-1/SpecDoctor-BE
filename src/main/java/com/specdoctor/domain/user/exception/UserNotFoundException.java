package com.specdoctor.domain.user.exception;

import com.specdoctor.global.error.ErrorCode;
import com.specdoctor.global.error.exception.ServiceException;

public class UserNotFoundException extends ServiceException {
	public UserNotFoundException() {
		super(ErrorCode.USER_NOT_FOUND);
	}
}
