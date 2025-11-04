package com.practice.settleadmin.presentation.dto.response.admin;

import java.time.LocalDateTime;

public record AdminCreateStoreOwnerResDto(
	String email,
	String name,
	String businessNumber,
	LocalDateTime createdAt
) {
}