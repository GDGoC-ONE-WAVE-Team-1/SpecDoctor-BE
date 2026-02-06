package com.specdoctor.domain.review.dto;

public record ReviewCreateRequest(
        String name,
        String review,
        int star,
        String role
) {

}
