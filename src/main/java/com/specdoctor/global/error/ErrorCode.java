package com.specdoctor.global.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	BAD_GOOGLE_TOKEN(HttpStatus.BAD_REQUEST.value(), "BAD_GOOGLE_TOKEN", "잘못된 Google OAuth 토큰입니다."),

	INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "INVALID_TOKEN", "유효하지 않은 토큰입니다."),
	MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "MALFORMED_TOKEN", "위/변조된 토큰입니다."),
	UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "UNSUPPORTED_TOKEN", "지원하지 않는 토큰입니다."),
	EMPTY_AUTHENTICATION(HttpStatus.UNAUTHORIZED.value(), "EMPTY_AUTHENTICATION", "인증정보가 존재하지 않습니다."),
	EXPIRED_TOKEN(HttpStatus.FORBIDDEN.value(), "EXPIRED_TOKEN", "만료된 토큰입니다."),

	ROLE_FORBIDDEN(HttpStatus.FORBIDDEN.value(), "ROLE_FORBIDDEN", "액세스할 수 있는 권한이 아닙니다."),

	USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "USER_NOT_FOUND", "존재하지 않는 사용자입니다."),
	REPORT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "REPORT_NOT_FOUND", "존재하지 않는 신고입니다."),

	FAIL_GOOGLE_OAUTH(HttpStatus.INTERNAL_SERVER_ERROR.value(), "FAIL_GOOGLE_OAUTH", "Google OAuth에 실패했습니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다.");

	private final int httpStatus;
	private final String code;
	private final String message;
}
