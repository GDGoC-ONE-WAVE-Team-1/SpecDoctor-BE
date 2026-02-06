package com.specdoctor.global.error;

import lombok.Builder;

@Builder
public record ErrorResponse(int httpStatus,
							String message,
							String code) {

	public static ErrorResponse of(ErrorCode errorCode) {
		return ErrorResponse.builder()
			.httpStatus(errorCode.getHttpStatus())
			.message(errorCode.getMessage())
			.code(errorCode.getCode())
			.build();
	}
}
