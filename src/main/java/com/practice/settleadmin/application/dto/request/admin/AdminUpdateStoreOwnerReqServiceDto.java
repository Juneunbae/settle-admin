package com.practice.settleadmin.application.dto.request.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdminUpdateStoreOwnerReqServiceDto(
	Long storeOwnerId,

	@NotBlank
	@Size(max = 20)
	String name,

	@NotBlank
	@Pattern(
		regexp = "^\\d{3}-\\d{2}-\\d{5}$",
		message = "유효하지 않은 사업자등록번호입니다."
	)
	String businessNumber
) {
}