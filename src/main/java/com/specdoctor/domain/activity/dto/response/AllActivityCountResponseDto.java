package com.specdoctor.domain.activity.dto.response;

public record AllActivityCountResponseDto(
	long totalActivityCount,
	long reportedActivityCount
) {
}
