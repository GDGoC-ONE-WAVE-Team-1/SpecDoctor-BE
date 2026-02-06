package com.specdoctor.domain.invalidactivity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.specdoctor.domain.invalidactivity.service.AddReportedActivityService;
import com.specdoctor.global.success.SuccessCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/invalid-activity")
@RequiredArgsConstructor
public class InvalidActivityController {

	private final AddReportedActivityService addReportedActivityService;

	@PostMapping("/reported-activity")
	public String addReportedActivityService(@RequestParam Long id) {
		addReportedActivityService.execute(id);

		return SuccessCode.CREATED.getMessage();
	}
}
