package com.specdoctor.global.success;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

	SUCCESS(200, "SUCCESS", "성공"),
	CREATED(201, "CREATED", "생성 완료");

	private final int httpStatus;
	private final String code;
	private final String message;
}
