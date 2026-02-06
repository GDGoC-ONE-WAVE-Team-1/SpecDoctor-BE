package com.specdoctor.domain.activity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.specdoctor.domain.activity.dto.response.SearchActivityResponseDto;
import com.specdoctor.domain.activity.service.SearchActivityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/activity")
@RequiredArgsConstructor
public class ActivityController {

	private final SearchActivityService searchActivityService;

	@GetMapping("/search")
	public SearchActivityResponseDto searchActivity(@RequestParam String activityName) {
		return searchActivityService.execute(activityName);
	}
}
