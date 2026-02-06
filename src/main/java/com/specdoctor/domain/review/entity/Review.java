package com.specdoctor.domain.review.entity;

import com.specdoctor.domain.review.dto.ReviewUpdateRequest;
import com.specdoctor.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review extends BaseEntity {

    @NotNull
    @Column
    private Long userId;

    @NotNull
    @Column
    private Long activityId;

    @Column(columnDefinition = "TEXT")
    private String review;

    @NotNull
    @Column
    private int star;

    @NotNull
    @Column
    private String role;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ReviewStatus status = ReviewStatus.ACTIVE;

    public enum ReviewStatus {
        ACTIVE, DELETED, PENDING
    }

    // 수정 로직
    public void update(ReviewUpdateRequest request) {
        this.review = request.review();
        this.star = request.star();
        this.role = request.role();
    }

    // 소유자 확인 (userId 기준)
    public boolean isOwner(Long memberId) {
        return this.userId.equals(memberId);
    }
}