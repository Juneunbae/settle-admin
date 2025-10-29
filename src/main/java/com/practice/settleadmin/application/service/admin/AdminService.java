package com.practice.settleadmin.application.service.admin;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.practice.settleadmin.application.dto.mapper.admin.AdminApplicationMapper;
import com.practice.settleadmin.application.dto.request.admin.AdminSignUpRequestServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminSignUpResponseServiceDto;
import com.practice.settleadmin.domain.member.Member;
import com.practice.settleadmin.domain.member.service.MemberRegistration;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class AdminService {
	private final AdminApplicationMapper mapper;
	private final MemberRegistration memberRegistration;

	public AdminSignUpResponseServiceDto signup(@Valid AdminSignUpRequestServiceDto request) {
		Member newAdmin = memberRegistration.adminRegister(
			request.email(), request.password(), request.name(), request.employeeNumber()
		);
		return mapper.toAdminSignUpResponseServiceDto(newAdmin);
	}
}