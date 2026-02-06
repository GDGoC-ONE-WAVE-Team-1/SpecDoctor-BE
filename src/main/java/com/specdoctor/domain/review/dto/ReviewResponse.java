package com.specdoctor.domain.review.dto;

import com.specdoctor.domain.review.entity.Review;

public record ReviewResponse(
	Long id,
	String writer,
	String review,
	int star,
	String role
) {
	public static ReviewResponse from(Review review) {
		return new ReviewResponse(
			review.getId(),
			review.getWriter().getName(),
			review.getReview(),
			review.getStar(),
			review.getRole()
		);
	}
}
