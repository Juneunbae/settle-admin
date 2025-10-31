package com.practice.settleadmin.presentation.dto.request.admin;

public record AdminPromotionRequestDto(
	String email,
	String employeeNumber,
	String promotionCode
) {
}