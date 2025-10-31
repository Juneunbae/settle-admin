package com.practice.settleadmin.presentation.dto.response.admin;

import java.time.LocalDateTime;

public record AdminPromotionResponseDto(
	String email,
	String message,
	LocalDateTime updatedAt
) {
}