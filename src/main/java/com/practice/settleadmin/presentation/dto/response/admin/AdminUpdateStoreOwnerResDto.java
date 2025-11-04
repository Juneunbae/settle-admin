package com.practice.settleadmin.presentation.dto.response.admin;

import java.time.LocalDateTime;

public record AdminUpdateStoreOwnerResDto(
	String email,
	String name,
	String businessNumber,
	LocalDateTime updatedAt
) {
}