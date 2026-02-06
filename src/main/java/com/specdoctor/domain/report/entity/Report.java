package com.specdoctor.domain.report.entity;

import com.specdoctor.global.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Report extends BaseEntity {

	@NotNull
	@Column(length = 50)
	private String name;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String message;

	@Column(columnDefinition = "TEXT")
	private String operationEntity;

	@Column(columnDefinition = "TEXT")
	private String finance;

	@Column(columnDefinition = "TEXT")
	private String financeOpen;

	@Column(columnDefinition = "TEXT")
	private String leaderSelection;
}
