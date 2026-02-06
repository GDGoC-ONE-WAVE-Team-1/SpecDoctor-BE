package com.specdoctor.domain.review.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.specdoctor.domain.review.dto.ReviewCreateRequest;
import com.specdoctor.domain.review.dto.ReviewResponse;
import com.specdoctor.domain.review.service.ReviewService;
import com.specdoctor.global.success.SuccessCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping
	public String createReview(
		@AuthenticationPrincipal Long id,
		@RequestBody ReviewCreateRequest request
	) {
		reviewService.create(id, request);
		return SuccessCode.CREATED.getMessage();
	}

	@GetMapping
	public List<ReviewResponse> getAllReviews(@RequestParam String activityName) {
		return reviewService.findAll(activityName);
	}
}
