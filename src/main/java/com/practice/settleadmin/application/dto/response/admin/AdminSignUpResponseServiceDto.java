package com.practice.settleadmin.application.dto.response.admin;

import java.time.LocalDateTime;

public record AdminSignUpResponseServiceDto(
	String email,
	String name,
	String employeeNumber,
	LocalDateTime createdAt
) {
}