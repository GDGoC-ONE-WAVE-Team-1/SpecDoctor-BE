package com.specdoctor.domain.report.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.specdoctor.domain.report.entity.Report;
import com.specdoctor.domain.report.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAllReportService {

	private final ReportRepository reportRepository;

	public List<Report> execute() {
		return reportRepository.findAll();
	}
}
