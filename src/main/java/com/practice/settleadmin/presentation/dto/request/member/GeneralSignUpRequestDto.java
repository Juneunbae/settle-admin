package com.practice.settleadmin.presentation.dto.request.member;

public record GeneralSignUpRequestDto(
	String email,
	String password,
	String name
) {
}