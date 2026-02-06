package com.specdoctor.domain.dto;

public record ReviewUpdateRequest  (
        String review,
        int star,
        String role,
        String url
) {

}
