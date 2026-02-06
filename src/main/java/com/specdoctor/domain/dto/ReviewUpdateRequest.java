package com.specdoctor.domain.dto;

public record ReviewUpdateRequest  (
        Long activityId,
        String review,
        int star,
        String role,
        String url
) {

}
