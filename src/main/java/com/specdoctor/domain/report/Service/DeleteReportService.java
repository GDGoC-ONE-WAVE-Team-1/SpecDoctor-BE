package com.specdoctor.domain.report.Service;

import org.springframework.stereotype.Service;

import com.specdoctor.domain.report.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteReportService {

	private final ReportRepository reportRepository;

	public void execute(Long reportId) {
		reportRepository.deleteById(reportId);
	}
}
