package com.specdoctor.domain.report.Service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.specdoctor.domain.activity.dto.response.SearchActivityResponseDto;
import com.specdoctor.domain.activity.service.AiSearchActivityService;
import com.specdoctor.domain.invalidactivity.dto.response.InvalidActivityResponseDto;
import com.specdoctor.domain.report.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsyncReportAnalysisService {

	private final ReportRepository reportRepository;
	private final AiSearchActivityService aiSearchActivityService;

	@Async
	@Transactional
	public void analyzeReport(Long reportId, String activityName) {
		SearchActivityResponseDto result = aiSearchActivityService.execute(activityName, false);

		if (!result.isValid()) {
			InvalidActivityResponseDto invalidActivityResponseDto = (InvalidActivityResponseDto)result.result();

			reportRepository.findById(reportId)
				.ifPresent(report -> report.updateAiAnalysis(invalidActivityResponseDto));
		}
	}
}
