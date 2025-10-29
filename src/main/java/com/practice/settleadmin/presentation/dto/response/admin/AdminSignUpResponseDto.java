package com.practice.settleadmin.presentation.dto.response.admin;

import java.time.LocalDateTime;

public record AdminSignUpResponseDto(
	String email,
	String name,
	String employeeNumber,
	LocalDateTime createdAt
) {
}