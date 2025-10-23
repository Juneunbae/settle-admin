package com.practice.settleadmin.domain.member;

import lombok.Getter;

@Getter
public enum Role {
	GENERAL("일반 회원"),
	OPERATION("운영팀"),
	OPERATION_LEADER("운영팀장"),
	STORE_OWNER("점주");

	private final String description;

	Role(String description) {
		this.description = description;
	}
}