package com.practice.settleadmin.application.dto.response.member;

import java.time.LocalDateTime;

public record GeneralSignUpResponseServiceDto(
	String email,
	String name,
	LocalDateTime createdAt
) {
}