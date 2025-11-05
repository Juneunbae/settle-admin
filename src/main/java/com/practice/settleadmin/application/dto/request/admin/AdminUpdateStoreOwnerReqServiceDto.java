package com.practice.settleadmin.application.dto.request.admin;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdminUpdateStoreOwnerReqServiceDto(
	Long storeOwnerId,

	@Size(max = 20)
	String name,

	@Pattern(
		regexp = "^\\d{3}-\\d{2}-\\d{5}$",
		message = "유효하지 않은 사업자등록번호입니다."
	)
	String businessNumber
) {
}