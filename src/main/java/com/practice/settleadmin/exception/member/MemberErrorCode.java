package com.practice.settleadmin.exception.member;

import org.springframework.http.HttpStatus;

import com.practice.settleadmin.exception.BaseErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {
	ALREADY_EXIST_EMAIL("M-001", "이미 사용중인 이메일입니다.", HttpStatus.BAD_REQUEST),
	ALREADY_EXIST_EMAIL_OR_EMPLOYEE_NUMBER("M-002", "이미 사용중인 이메일 또는 사원 번호입니다.", HttpStatus.BAD_REQUEST),
	INVALID_CODE("M-003", "유효하지 않은 관리자 코드입니다.", HttpStatus.BAD_REQUEST),
	NOT_EXIST("M-004", "존재하지 않는 유저입니다.", HttpStatus.NOT_FOUND),
	ACCESS_DENIED("M-005", "권한이 존재하지 않습니다.", HttpStatus.FORBIDDEN),
	ALREADY_EXIST_INFORMATION("M-006", "이미 사용중인 정보가 있습니다.", HttpStatus.BAD_REQUEST),
	NOT_ACCESS_SAME_VALUE("M-007", "같은 정보로 수정을 할 수 없습니다.", HttpStatus.BAD_REQUEST);

	private final String errorCode;
	private final String message;
	private final HttpStatus status;
}