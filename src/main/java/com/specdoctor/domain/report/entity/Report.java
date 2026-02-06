package com.specdoctor.domain.report.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

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
@SQLDelete(sql = "UPDATE report SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
@SQLRestriction("is_deleted = false")
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

	@Builder.Default
	@Column(nullable = false)
	private Boolean isDeleted = false;

	@Column
	private LocalDateTime deletedAt;

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
