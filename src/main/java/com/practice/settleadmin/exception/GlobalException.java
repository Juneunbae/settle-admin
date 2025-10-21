package com.practice.settleadmin.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
	private final BaseErrorCode baseErrorCode;

	public GlobalException(BaseErrorCode baseErrorCode) {
		super(baseErrorCode.getMessage());
		this.baseErrorCode = baseErrorCode;
	}
}