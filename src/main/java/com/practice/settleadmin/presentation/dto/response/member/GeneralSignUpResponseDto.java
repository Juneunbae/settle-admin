package com.practice.settleadmin.presentation.dto.response.member;

import java.time.LocalDateTime;

public record GeneralSignUpResponseDto(
	String email,
	String name,
	LocalDateTime createdAt
) {
}