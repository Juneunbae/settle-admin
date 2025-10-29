package com.practice.settleadmin.exception.member;

import org.springframework.http.HttpStatus;

import com.practice.settleadmin.exception.BaseErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {
	ALREADY_EXIST_EMAIL("M-001", "이미 사용중인 이메일입니다.", HttpStatus.BAD_REQUEST),
	ALREADY_EXIST_EMAIL_OR_EMPLOYEE_NUMBER("M-002", "이미 사용중인 이메일 또는 사원 번호입니다.", HttpStatus.BAD_REQUEST);

	private final String errorCode;
	private final String message;
	private final HttpStatus status;
}