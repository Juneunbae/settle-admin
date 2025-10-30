package com.practice.settleadmin.application.service.member;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.practice.settleadmin.application.dto.mapper.member.MemberApplicationMapper;
import com.practice.settleadmin.application.dto.request.member.GeneralSignUpRequestServiceDto;
import com.practice.settleadmin.application.dto.response.member.GeneralSignUpResponseServiceDto;
import com.practice.settleadmin.domain.member.Member;
import com.practice.settleadmin.domain.member.service.MemberRegistration;
import com.practice.settleadmin.exception.GlobalException;
import com.practice.settleadmin.exception.member.MemberErrorCode;
import com.practice.settleadmin.infrastructure.member.MemberRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class MemberService {
	private final MemberApplicationMapper mapper;
	private final MemberRepository memberRepository;
	private final MemberRegistration memberRegistration;

	@Transactional
	public GeneralSignUpResponseServiceDto signup(@Valid GeneralSignUpRequestServiceDto request) {
		existsByEmail(request.email());
		Member newMember = memberRegistration.register(request.email(), request.password(), request.name());
		return mapper.toGeneralSignUpResponseServiceDto(newMember);
	}

	public void existsByEmail(String email) {
		if (memberRepository.existsByEmail(email)) {
			throw new GlobalException(MemberErrorCode.ALREADY_EXIST_EMAIL);
		}
	}
}