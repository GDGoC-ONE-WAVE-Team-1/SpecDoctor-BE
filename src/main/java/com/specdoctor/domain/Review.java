package com.specdoctor.domain;

import com.specdoctor.domain.dto.ReviewUpdateRequest;
import com.specdoctor.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*; // AllArgsConstructor 추가를 위해 수정

@Entity
@Getter
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor // 1. 빌더 사용을 위해 추가
@Builder // 2. 이제 정상 작동합니다.
public class Review extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // ID 필드가 누락되었다면 추가 확인
    private Long id;

    @Column(name = "reviewer_id", nullable = false)
    private Long reviewerId;

    @Column(columnDefinition = "TEXT")
    private String review;

    private int star;
    private String role;
    private String url;

    @Enumerated(EnumType.STRING)
    @Builder.Default // 빌더 사용 시 기본값 설정을 위해 추가
    private ReviewStatus status = ReviewStatus.ACTIVE;

    public enum ReviewStatus {
        ACTIVE, DELETED, PENDING
    }

    public void update(ReviewUpdateRequest request) {
        this.review = request.review();
        this.star = request.star();
        this.role = request.role();
        this.url = request.url();
    }

    public boolean isOwner(Long memberId) {
        return this.reviewerId.equals(memberId);
    }
}