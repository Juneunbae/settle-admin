package com.practice.settleadmin.presentation.dto.request.admin;

public record AdminUpdateStoreOwnerReqDto(
	Long storeOwnerId,
	String name,
	String businessNumber
) {
}