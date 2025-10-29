package com.practice.settleadmin.domain.member;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(
		length = 50,
		nullable = false
	)
	private String email;

	@Column(
		length = 256,
		nullable = false
	)
	private String password;

	@Column(
		length = 20,
		nullable = false
	)
	private String name;

	@Column(
		length = 20,
		nullable = false
	)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(
		length = 30
	)
	private String employeeNumber;

	@Column(
		length = 20
	)
	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(
		nullable = false
	)
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public static Member create(String email, String password, String name) {
		return Member.builder()
			.email(email)
			.password(password)
			.name(name)
			.role(Role.GENERAL)
			.status(Status.ACTIVATE)
			.createdAt(LocalDateTime.now())
			.build();
	}

	public static Member adminCreate(String email, String password, String name, String employeeNumber) {
		return Member.builder()
			.email(email)
			.password(password)
			.name(name)
			.role(Role.OPERATION)
			.employeeNumber(employeeNumber)
			.status(Status.ACTIVATE)
			.createdAt(LocalDateTime.now())
			.build();
	}
}