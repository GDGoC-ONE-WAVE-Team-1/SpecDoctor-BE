package com.specdoctor.domain.dto;

public record ReviewCreateRequest(
        String review,
        int star,
        String role,
        String url
) {

}
