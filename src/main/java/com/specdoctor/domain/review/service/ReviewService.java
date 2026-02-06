package com.specdoctor.domain.review.service;

import com.specdoctor.domain.review.entity.Review;
import com.specdoctor.domain.review.dto.ReviewCreateRequest;
import com.specdoctor.domain.review.dto.ReviewResponse;
import com.specdoctor.domain.review.dto.ReviewUpdateRequest;
import com.specdoctor.domain.review.repository.ReviewRepository;
import com.specdoctor.global.error.ErrorCode;
import com.specdoctor.global.error.exception.ServiceException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    @Builder
    @RequestMapping("api/v1/activity")
    public Long create(Long memberId, ReviewCreateRequest request) {
        Review review = Review.builder()
                .userId(memberId)
                .activityId(request.activityId())
                .review(request.review())
                .star(request.star())
                .role(request.role())
                .build();

        return reviewRepository.save(review).getId();
    }

    public List<ReviewResponse> findAll() {
        return reviewRepository.findAll().stream()
                .map(ReviewResponse::from)
                .toList();
    }

    @Transactional
    public void update(Long memberId, Long reviewId, ReviewUpdateRequest request) {
        Review review = findReviewOrThrow(reviewId);

        if (!review.isOwner(memberId)) {
            throw new ServiceException(ErrorCode.ROLE_FORBIDDEN);
        }

        review.update(request);
    }

    @Transactional
    public void delete(Long memberId, Long reviewId) {
        Review review = findReviewOrThrow(reviewId);

        if (!review.isOwner(memberId)) {
            throw new ServiceException(ErrorCode.ROLE_FORBIDDEN);
        }

        reviewRepository.delete(review);
    }

    private Review findReviewOrThrow(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));
    }

}
