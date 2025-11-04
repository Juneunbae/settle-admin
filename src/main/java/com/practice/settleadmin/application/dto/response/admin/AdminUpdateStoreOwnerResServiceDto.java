package com.practice.settleadmin.application.dto.response.admin;

import java.time.LocalDateTime;

public record AdminUpdateStoreOwnerResServiceDto(
	String email,
	String name,
	String businessNumber,
	LocalDateTime updatedAt
) {
}