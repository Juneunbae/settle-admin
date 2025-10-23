package com.practice.settleadmin.application.service.member;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.practice.settleadmin.application.dto.mapper.member.MemberApplicationMapper;
import com.practice.settleadmin.application.dto.request.member.GeneralSignUpRequestServiceDto;
import com.practice.settleadmin.application.dto.response.member.GeneralSignUpResponseServiceDto;
import com.practice.settleadmin.domain.member.Member;
import com.practice.settleadmin.domain.member.service.MemberRegistration;

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
	private final MemberRegistration memberRegistration;

	@Transactional
	public GeneralSignUpResponseServiceDto signup(@Valid GeneralSignUpRequestServiceDto request) {
		Member newMember = memberRegistration.register(request.email(), request.password(), request.name());
		return mapper.toGeneralSignUpResponseServiceDto(newMember);
	}
}