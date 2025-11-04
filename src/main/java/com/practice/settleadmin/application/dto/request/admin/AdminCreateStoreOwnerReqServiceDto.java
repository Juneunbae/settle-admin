package com.practice.settleadmin.application.dto.request.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdminCreateStoreOwnerReqServiceDto(
	Long adminId,

	@NotBlank
	@Size(max = 50)
	@Email(message = "이메일 형식이 잘못되었습니다.")
	String email,

	@NotBlank
	@Size(max = 256)
	@Pattern(
		regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()_+\\-={}\\[\\]|:;\"'<>,.?/]).{8,}$",
		message = "비밀번호는 영문, 숫자, 특수기호를 각각 1개 이상 포함하고 8자 이상이어야 합니다."
	)
	String password,

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