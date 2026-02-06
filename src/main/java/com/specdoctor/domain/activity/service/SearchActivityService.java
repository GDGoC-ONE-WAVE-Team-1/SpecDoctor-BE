package com.specdoctor.domain.activity.service;

import org.springframework.stereotype.Service;

import com.specdoctor.domain.activity.dto.response.ActivityInfoResponseDto;
import com.specdoctor.domain.activity.dto.response.SearchActivityResponseDto;
import com.specdoctor.domain.activity.repository.ActivityRepository;
import com.specdoctor.domain.invalidactivity.dto.response.InvalidActivityResponseDto;
import com.specdoctor.domain.invalidactivity.repository.InvalidActivityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchActivityService {

	private final ActivityRepository activityRepository;
	private final InvalidActivityRepository invalidActivityRepository;

	public SearchActivityResponseDto execute(String activityName) {
		return activityRepository.findByName(activityName)
			.map(activity -> new SearchActivityResponseDto(true, ActivityInfoResponseDto.of(activity)))
			.orElseGet(() ->
				invalidActivityRepository.findByName(activityName)
					.map(invalidActivity -> new SearchActivityResponseDto(false, InvalidActivityResponseDto.of(invalidActivity)))
					.orElseGet(() -> {
						// TODO: AI 기반 분석
						return new SearchActivityResponseDto(true, null);
					})
			);
	}
}
