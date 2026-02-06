package com.specdoctor.domain.activity.entity.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Category {

	IT("IT"),
	DEVELOPMENT("개발"),
	DESIGN("디자인"),
	PLANNING("기획"),
	MARKETING("마케팅"),
	ETA("기타");

	private final String name;
}
