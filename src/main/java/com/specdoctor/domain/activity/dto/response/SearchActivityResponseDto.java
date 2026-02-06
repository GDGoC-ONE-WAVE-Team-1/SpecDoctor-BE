package com.specdoctor.domain.activity.dto.response;

public record SearchActivityResponseDto(
	boolean isValid,
	SearchResultResponseDto result
) {
}
