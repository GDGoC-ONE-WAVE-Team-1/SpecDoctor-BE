package com.specdoctor.domain.invalidactivity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.specdoctor.domain.activity.entity.Activity;
import com.specdoctor.domain.invalidactivity.entity.InvalidActivity;

public interface InvalidActivityRepository extends JpaRepository<InvalidActivity, Long> {

	Optional<InvalidActivity> findByName(String name);
}
