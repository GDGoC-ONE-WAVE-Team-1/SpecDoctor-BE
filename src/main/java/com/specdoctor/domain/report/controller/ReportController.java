package com.specdoctor.domain.report.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.specdoctor.domain.report.Service.CreateReportService;
import com.specdoctor.domain.report.dto.request.CreateReportRequest;
import com.specdoctor.global.success.SuccessCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/report")
@RequiredArgsConstructor
public class ReportController {

	private final CreateReportService createReportService;

	@PostMapping
	public String createReport(@RequestBody CreateReportRequest createReportRequest) {
		createReportService.createReport(createReportRequest);

		return SuccessCode.CREATED.getMessage();
	}
}
