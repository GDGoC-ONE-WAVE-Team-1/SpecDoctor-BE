package com.specdoctor.domain.review.repository;

import com.specdoctor.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByActivityName(String name);

}