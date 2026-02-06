package com.specdoctor.domain.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.specdoctor.domain.report.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

	@Query(value = "SELECT count(*) FROM report", nativeQuery = true)
	long countAllIncludingDeleted();
}
