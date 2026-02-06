package com.specdoctor.domain.invalidactivity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.specdoctor.domain.activity.entity.Activity;
import com.specdoctor.domain.invalidactivity.entity.InvalidActivity;

public interface InvalidActivityRepository extends JpaRepository<InvalidActivity, Long> {

	boolean existsByName(String name);
}
