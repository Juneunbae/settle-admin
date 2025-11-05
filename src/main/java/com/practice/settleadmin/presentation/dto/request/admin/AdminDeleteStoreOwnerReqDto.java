package com.practice.settleadmin.presentation.dto.request.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminDeleteStoreOwnerReqDto(
	@NotBlank
	@Size(max = 50)
	@Email(message = "이메일 형식이 잘못되었습니다.")
	String email
) {
}