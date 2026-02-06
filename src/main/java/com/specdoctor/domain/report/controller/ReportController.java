package com.specdoctor.domain.report.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.specdoctor.domain.report.Service.CreateReportService;
import com.specdoctor.domain.report.Service.DeleteReportService;
import com.specdoctor.domain.report.Service.GetAllReportService;
import com.specdoctor.domain.report.dto.request.CreateReportRequest;
import com.specdoctor.domain.report.entity.Report;
import com.specdoctor.global.success.SuccessCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/report")
@RequiredArgsConstructor
public class ReportController {

	private final CreateReportService createReportService;
	private final GetAllReportService getAllReportService;
	private final DeleteReportService deleteReportService;

	@PostMapping
	public String createReport(@RequestBody CreateReportRequest createReportRequest) {
		createReportService.createReport(createReportRequest);

		return SuccessCode.CREATED.getMessage();
	}

	@GetMapping
	public List<Report> getAllReports() {
		return getAllReportService.execute();
	}

	@DeleteMapping("/{id}")
	public String deleteReport(@PathVariable Long id) {
		deleteReportService.execute(id);

		return SuccessCode.SUCCESS.getMessage();
	}
}
