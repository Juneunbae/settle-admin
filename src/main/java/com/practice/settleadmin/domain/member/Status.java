package com.practice.settleadmin.domain.member;

import lombok.Getter;

@Getter
public enum Status {
	ACTIVATE("활성화"),
	INACTIVATE("비활성화");

	private final String Description;

	Status(String description) {
		this.Description = description;
	}
}