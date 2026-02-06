package com.specdoctor.domain.user.entity;

import com.specdoctor.domain.user.entity.enums.Role;
import com.specdoctor.global.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
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
public class User extends BaseEntity {

	@NotNull
	@Column(length = 100)
	private String name;

	@NotNull
	@Column(length = 50)
	private String email;

	@NotNull
	@Column
	private int ticketCount;

	@NotNull
	@Column
	private Boolean isSubscribe;

	@NotNull
	@Column
	@Enumerated(EnumType.STRING)
	private Role role;

	public static User create(String name, String email, Role role) {
		return User.builder()
			.name(name)
			.email(email)
			.ticketCount(0)
			.isSubscribe(false)
			.role(role)
			.build();
	}
}

