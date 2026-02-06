package com.specdoctor.domain.report.entity;

import com.specdoctor.domain.invalidactivity.dto.response.InvalidActivityResponseDto;
import com.specdoctor.domain.report.dto.request.CreateReportRequest;
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

	public static Report from(CreateReportRequest request) {
		return Report.builder()
			.name(request.name())
			.message(request.message())
			.build();
	}

	public void updateAiAnalysis(
		InvalidActivityResponseDto invalidActivityResponseDto
	) {
		this.operationEntity = invalidActivityResponseDto.operationEntity();
		this.finance = invalidActivityResponseDto.finance();
		this.financeOpen = invalidActivityResponseDto.financeOpen();
		this.leaderSelection = invalidActivityResponseDto.leaderSelection();
	}
}
