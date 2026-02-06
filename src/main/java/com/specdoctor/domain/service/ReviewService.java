package com.specdoctor.domain.service;

import com.specdoctor.domain.Review;
import com.specdoctor.domain.dto.ReviewCreateRequest;
import com.specdoctor.domain.dto.ReviewUpdateRequest;
import com.specdoctor.domain.repository.ReviewRepository;
import com.specdoctor.global.error.ErrorCode;
import com.specdoctor.global.error.exception.ServiceException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    @Builder
    public Long create(Long memberId, ReviewCreateRequest request) {
        Review review = Review.builder()
                .reviewerId(memberId)
                .review(request.review())
                .star(request.star())
                .role(request.role())
                .url(request.url())
                .build();

        return reviewRepository.save(review).getId();
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