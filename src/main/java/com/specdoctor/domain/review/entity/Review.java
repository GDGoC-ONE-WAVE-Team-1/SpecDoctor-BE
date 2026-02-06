package com.specdoctor.domain.review.entity;

import javax.validation.constraints.NotNull;

import com.specdoctor.domain.activity.entity.Activity;
import com.specdoctor.domain.user.entity.User;
import com.specdoctor.global.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Review extends BaseEntity {

	@JoinColumn(name = "writer_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User writer;

	@JoinColumn(name = "activity_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Activity activity;

	@Column(columnDefinition = "TEXT")
	private String review;

	@NotNull
	@Column
	private int star;

	@NotNull
	@Column
	private String role;
}
