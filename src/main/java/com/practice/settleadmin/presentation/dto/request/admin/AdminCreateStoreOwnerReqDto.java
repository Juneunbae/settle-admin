package com.practice.settleadmin.presentation.dto.request.admin;

public record AdminCreateStoreOwnerReqDto(
	Long adminId,
	String email,
	String password,
	String name,
	String businessNumber
) {
}