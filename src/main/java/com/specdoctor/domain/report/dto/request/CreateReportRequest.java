package com.specdoctor.domain.report.dto.request;

public record CreateReportRequest(
	String name,
	String message
) {
}
