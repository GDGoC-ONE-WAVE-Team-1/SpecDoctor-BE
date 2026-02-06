package com.specdoctor.domain.activity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.specdoctor.domain.activity.entity.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

	Optional<Activity> findByName(String name);

	int countAll();
}
