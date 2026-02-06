package com.specdoctor.domain.dto;

public record ReviewCreateRequest(
        Long activityId,
        String review,
        int star,
        String role,
        String url
) {

}
