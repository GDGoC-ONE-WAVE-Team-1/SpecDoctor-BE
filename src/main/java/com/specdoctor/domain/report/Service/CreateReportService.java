package com.specdoctor.domain.report.Service;

import org.springframework.stereotype.Service;

import com.specdoctor.domain.invalidactivity.repository.InvalidActivityRepository;
import com.specdoctor.domain.report.dto.request.CreateReportRequest;
import com.specdoctor.domain.report.entity.Report;
import com.specdoctor.domain.report.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateReportService {

	private final ReportRepository reportRepository;
	private final InvalidActivityRepository invalidActivityRepository;
	private final AsyncReportAnalysisService asyncReportAnalysisService;

	public void createReport(CreateReportRequest createReportRequest) {
		if (invalidActivityRepository.existsByName(createReportRequest.name())) {
			return;
		}

		Report report = Report.from(createReportRequest);

		Report savedReport = reportRepository.save(report);

		asyncReportAnalysisService.analyzeReport(savedReport.getId(), savedReport.getName());
	}
}
