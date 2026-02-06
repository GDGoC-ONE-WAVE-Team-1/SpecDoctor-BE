package com.specdoctor.domain.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.specdoctor.domain.activity.entity.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

	boolean existsByName(String name);
}
