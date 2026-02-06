package com.specdoctor.domain.invalidactivity.service;

import org.springframework.stereotype.Service;

import com.specdoctor.domain.invalidactivity.entity.InvalidActivity;
import com.specdoctor.domain.invalidactivity.repository.InvalidActivityRepository;
import com.specdoctor.domain.report.entity.Report;
import com.specdoctor.domain.report.repository.ReportRepository;
import com.specdoctor.global.error.ErrorCode;
import com.specdoctor.global.error.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddReportedActivityService {

	private final InvalidActivityRepository invalidActivityRepository;
	private final ReportRepository reportRepository;

	public void execute(Long ReportId) {
		Report report = reportRepository.findById(ReportId)
			.orElseThrow(() -> new ServiceException(ErrorCode.REPORT_NOT_FOUND));

		InvalidActivity invalidActivity = InvalidActivity.from(report);

		invalidActivityRepository.save(invalidActivity);
		reportRepository.delete(report);
	}
}
