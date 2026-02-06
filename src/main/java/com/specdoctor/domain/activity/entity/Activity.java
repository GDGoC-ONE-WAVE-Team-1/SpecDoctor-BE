package com.specdoctor.domain.activity.entity;

import com.specdoctor.domain.activity.entity.enums.Category;
import com.specdoctor.global.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Activity extends BaseEntity {

	@NotNull
	@Column(length = 100)
	private String name;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(columnDefinition = "TEXT")
	private String link;

	@Column
	@Enumerated(EnumType.STRING)
	private Category category;
}
