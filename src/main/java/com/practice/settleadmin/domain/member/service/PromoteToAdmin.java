package com.practice.settleadmin.domain.member.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.practice.settleadmin.domain.member.Member;
import com.practice.settleadmin.exception.GlobalException;
import com.practice.settleadmin.exception.member.MemberErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PromoteToAdmin {
	@Value("${admin.code}")
	private String code;

	public Member promote(Member admin, String promotionCode) {
		if (!promotionCode.equals(code)) {
			throw new GlobalException(MemberErrorCode.INVALID_CODE);
		}
		admin.promoteToAdmin();
		return admin;
	}
}