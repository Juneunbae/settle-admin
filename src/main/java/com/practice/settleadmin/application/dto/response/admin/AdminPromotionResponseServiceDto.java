package com.practice.settleadmin.application.dto.response.admin;

import java.time.LocalDateTime;

public record AdminPromotionResponseServiceDto(
	String email,
	String message,
	LocalDateTime updatedAt
) {
}