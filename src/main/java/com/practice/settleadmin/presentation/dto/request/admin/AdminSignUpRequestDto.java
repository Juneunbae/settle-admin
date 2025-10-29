package com.practice.settleadmin.presentation.dto.request.admin;

public record AdminSignUpRequestDto(
	String email,
	String password,
	String name,
	String employeeNumber
) {
}