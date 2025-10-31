package com.practice.settleadmin.application.dto.request.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdminPromotionRequestServiceDto(
	@NotBlank
	@Size(max = 50)
	@Email(message = "이메일 형식이 잘못되었습니다.")
	String email,

	@NotBlank
	@Pattern(
		regexp = "^\\d{2}-\\d{8}$",
		message = "유효하지 않은 사원번호입니다."
	)
	String employeeNumber,

	String promotionCode
) {
}