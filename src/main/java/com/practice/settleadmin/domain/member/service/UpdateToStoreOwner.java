package com.practice.settleadmin.domain.member.service;

import org.springframework.stereotype.Service;

import com.practice.settleadmin.application.dto.request.admin.AdminUpdateStoreOwnerReqServiceDto;
import com.practice.settleadmin.domain.member.Member;
import com.practice.settleadmin.exception.GlobalException;
import com.practice.settleadmin.exception.member.MemberErrorCode;
import com.practice.settleadmin.infrastructure.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateToStoreOwner {
	private final MemberRepository memberRepository;

	public Member update(AdminUpdateStoreOwnerReqServiceDto request, Member storeOwner) {
		if (storeOwner.getName().equals(request.name())
			|| storeOwner.getBusinessNumber().equals(request.businessNumber())
		) {
			throw new GlobalException(MemberErrorCode.NOT_ACCESS_SAME_VALUE);
		}

		storeOwner.storeOwnerUpdate(request.name(), request.businessNumber());
		return memberRepository.save(storeOwner);
	}
}