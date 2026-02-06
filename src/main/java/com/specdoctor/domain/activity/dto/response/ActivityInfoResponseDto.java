package com.specdoctor.domain.activity.dto.response;

import com.specdoctor.domain.activity.entity.Activity;
import com.specdoctor.domain.activity.entity.enums.Category;

public record ActivityInfoResponseDto(
	String name,
	String description,
	String link,
	Category category
	// TODO: Review 추가
) implements SearchResultResponseDto {

	public static ActivityInfoResponseDto of(Activity activity) {
		return new ActivityInfoResponseDto(
			activity.getName(),
			activity.getDescription(),
			activity.getLink(),
			activity.getCategory()
		);
	}
}
