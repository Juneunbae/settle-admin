package com.practice.settleadmin.application.dto.response.admin;

import java.time.LocalDateTime;

public record AdminCreateStoreOwnerResServiceDto(
	String email,
	String name,
	String businessNumber,
	LocalDateTime createdAt
) {
}