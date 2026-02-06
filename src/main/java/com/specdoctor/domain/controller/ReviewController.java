package com.specdoctor.domain.controller;

import com.specdoctor.domain.dto.ReviewCreateRequest;
import com.specdoctor.domain.dto.ReviewUpdateRequest;
import com.specdoctor.domain.service.ReviewService;
import com.specdoctor.global.auth.domain.AuthDetails;
import com.specdoctor.global.response.GlobalResponseDto;
import com.specdoctor.global.success.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<GlobalResponseDto<Long>> createReview(
            @AuthenticationPrincipal AuthDetails authDetails,
            @RequestBody ReviewCreateRequest request
    ) {
        Long reviewId = reviewService.create(authDetails.getUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GlobalResponseDto.success(reviewId, SuccessCode.CREATED));
    }

    @PutMapping("/{review_id}")
    public ResponseEntity<GlobalResponseDto<Void>> updateReview(
            @AuthenticationPrincipal AuthDetails authDetails,
            @PathVariable("review_id") Long reviewId,
            @RequestBody ReviewUpdateRequest request
    ) {
        reviewService.update(authDetails.getUserId(), reviewId, request);
        return ResponseEntity.ok(GlobalResponseDto.success(SuccessCode.SUCCESS));
    }

    @DeleteMapping("/{review_id}")
    public ResponseEntity<GlobalResponseDto<Void>> deleteReview(
            @AuthenticationPrincipal AuthDetails authDetails,
            @PathVariable("review_id") Long reviewId
    ) {
        reviewService.delete(authDetails.getUserId(), reviewId);
        return ResponseEntity.ok(GlobalResponseDto.success(SuccessCode.SUCCESS));
    }
}
