package com.specdoctor.domain.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.specdoctor.domain.report.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
