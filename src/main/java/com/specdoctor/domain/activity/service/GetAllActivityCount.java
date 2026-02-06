package com.specdoctor.domain.activity.service;

import org.springframework.stereotype.Service;

import com.specdoctor.domain.activity.dto.response.AllActivityCountResponseDto;
import com.specdoctor.domain.activity.repository.ActivityRepository;
import com.specdoctor.domain.report.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAllActivityCount {

	private final ActivityRepository activityRepository;
	private final ReportRepository reportRepository;

	public AllActivityCountResponseDto execute() {
		long totalActivityCount = activityRepository.countAll();
		long reportedActivityCount = reportRepository.countAllIncludingDeleted();

		return new AllActivityCountResponseDto(totalActivityCount, reportedActivityCount);
	}
}
