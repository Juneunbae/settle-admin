package com.practice.settleadmin.application.service.admin;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.practice.settleadmin.application.dto.mapper.admin.AdminApplicationMapper;
import com.practice.settleadmin.application.dto.request.admin.AdminSignUpRequestServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminSignUpResponseServiceDto;
import com.practice.settleadmin.domain.member.Member;
import com.practice.settleadmin.domain.member.service.MemberRegistration;
import com.practice.settleadmin.exception.GlobalException;
import com.practice.settleadmin.exception.member.MemberErrorCode;
import com.practice.settleadmin.infrastructure.member.MemberRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class AdminService {
	private final AdminApplicationMapper mapper;
	private final MemberRepository memberRepository;
	private final MemberRegistration memberRegistration;

	public AdminSignUpResponseServiceDto signup(@Valid AdminSignUpRequestServiceDto request) {
		existsByEmailOrEmployeeNumber(request.email(), request.employeeNumber());
		Member newAdmin = memberRegistration.adminRegister(
			request.email(), request.password(), request.name(), request.employeeNumber()
		);
		return mapper.toAdminSignUpResponseServiceDto(newAdmin);
	}

	public void existsByEmailOrEmployeeNumber(String email, String employeeNumber) {
		if (memberRepository.existsByEmailOrEmployeeNumber(email, employeeNumber)) {
			throw new GlobalException(MemberErrorCode.ALREADY_EXIST_EMAIL_OR_EMPLOYEE_NUMBER);
		}
	}
}