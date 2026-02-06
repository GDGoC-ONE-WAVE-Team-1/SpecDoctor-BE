package com.specdoctor.domain.repository;

import com.specdoctor.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 추가적인 쿼리가 필요하면 여기에 정의합니다.
}