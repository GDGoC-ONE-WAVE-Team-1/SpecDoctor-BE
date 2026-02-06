package com.specdoctor.domain.review.controller;

import com.specdoctor.domain.review.dto.ReviewCreateRequest;
import com.specdoctor.domain.review.dto.ReviewResponse;
import com.specdoctor.domain.review.dto.ReviewUpdateRequest;
import com.specdoctor.domain.review.service.ReviewService;
import com.specdoctor.domain.user.entity.User;
import com.specdoctor.global.auth.domain.AuthDetails;
import com.specdoctor.global.response.GlobalResponseDto;
import com.specdoctor.global.success.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
