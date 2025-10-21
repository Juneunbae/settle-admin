package com.practice.settleadmin.exception;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
	String getErrorCode();

	String getMessage();

	HttpStatus getStatus();
}