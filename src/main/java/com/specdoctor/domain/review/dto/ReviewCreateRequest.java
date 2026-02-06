package com.specdoctor.domain.review.dto;

public record ReviewCreateRequest(
        Long activityId,
        String review,
        int star,
        String role,
        String url
) {

}
