package com.specdoctor.domain.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.specdoctor.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	List<Review> findAllByActivityNameOrderByIdDesc(String name);

	List<Review> findAllByOrderByIdDesc();
}
