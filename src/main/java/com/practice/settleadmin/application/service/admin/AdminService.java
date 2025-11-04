package com.practice.settleadmin.application.service.admin;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.practice.settleadmin.application.dto.mapper.admin.AdminApplicationMapper;
import com.practice.settleadmin.application.dto.request.admin.AdminCreateStoreOwnerReqServiceDto;
import com.practice.settleadmin.application.dto.request.admin.AdminPromotionRequestServiceDto;
import com.practice.settleadmin.application.dto.request.admin.AdminSignUpRequestServiceDto;
import com.practice.settleadmin.application.dto.request.admin.AdminUpdateStoreOwnerReqServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminCreateStoreOwnerResServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminPromotionResponseServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminSignUpResponseServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminUpdateStoreOwnerResServiceDto;
import com.practice.settleadmin.domain.member.Member;
import com.practice.settleadmin.domain.member.Role;
import com.practice.settleadmin.domain.member.service.MemberRegistration;
import com.practice.settleadmin.domain.member.service.PromoteToAdmin;
import com.practice.settleadmin.domain.member.service.UpdateToStoreOwner;
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
public class AdminService {
	private final AdminApplicationMapper mapper;
	private final PromoteToAdmin promoteToAdmin;
	private final MemberRepository memberRepository;
	private final MemberRegistration memberRegistration;
	private final UpdateToStoreOwner updateToStoreOwner;

	@Transactional
	public AdminSignUpResponseServiceDto signup(@Valid AdminSignUpRequestServiceDto request) {
		existsByEmailOrEmployeeNumber(request.email(), request.employeeNumber());
		Member newAdmin = memberRegistration.adminRegister(
			request.email(), request.password(), request.name(), request.employeeNumber()
		);
		return mapper.toAdminSignUpResponseServiceDto(newAdmin);
	}

	@Transactional
	public AdminPromotionResponseServiceDto promoteToAdmin(@Valid AdminPromotionRequestServiceDto request) {
		Member admin = findByEmailAndEmployeeNumber(request.email(), request.employeeNumber());
		Member changeAdmin = promoteToAdmin.promote(admin, request.promotionCode());
		String message = "관리자 승인이 완료되었습니다.";
		return mapper.toAdminPromotionResponseServiceDto(changeAdmin, message);
	}

	@Transactional
	public AdminCreateStoreOwnerResServiceDto createStoreOwners(@Valid AdminCreateStoreOwnerReqServiceDto request) {
		// Security 연동 후 수정
		Member admin = findById(request.adminId());
		if (!admin.getRole().equals(Role.ADMIN)) {
			throw new GlobalException(MemberErrorCode.ACCESS_DENIED);
		}

		existsByEmailOrNameOrBusinessNumber(request.email(), request.name(), request.businessNumber());

		Member storeOwner = memberRegistration.storeOwnerRegister(
			request.email(), request.password(), request.name(), request.businessNumber()
		);
		return mapper.toAdminCreateStoreOwnerResServiceDto(storeOwner);
	}

	@Transactional
	public AdminUpdateStoreOwnerResServiceDto updateStoreOwners(@Valid AdminUpdateStoreOwnerReqServiceDto request) {
		Member storeOwner = this.findByIdForStoreOwner(request.storeOwnerId());
		existsByNameOrBusinessNumber(request.name(), request.businessNumber());
		Member updateStoreOwner = updateToStoreOwner.update(request, storeOwner);
		return mapper.toAdminUpdateStoreOwnerResServiceDto(updateStoreOwner);
	}

	public Member findById(Long id) {
		return memberRepository.findById(id)
			.orElseThrow(() -> new GlobalException(MemberErrorCode.NOT_EXIST));
	}

	public Member findByIdForStoreOwner(Long id) {
		Member storeOwner = this.findById(id);
		if (!storeOwner.getRole().equals(Role.STORE_OWNER)) {
			throw new GlobalException(MemberErrorCode.NOT_EXIST);
		}

		return storeOwner;
	}

	public void existsByEmailOrEmployeeNumber(String email, String employeeNumber) {
		if (memberRepository.existsByEmailOrEmployeeNumber(email, employeeNumber)) {
			throw new GlobalException(MemberErrorCode.ALREADY_EXIST_EMAIL_OR_EMPLOYEE_NUMBER);
		}
	}

	public void existsByNameOrBusinessNumber(String name, String businessNumber) {
		if (memberRepository.existsByNameOrBusinessNumber(name, businessNumber)) {
			throw new GlobalException(MemberErrorCode.ALREADY_EXIST_INFORMATION);
		}
	}

	public void existsByEmailOrNameOrBusinessNumber(String email, String name, String businessNumber) {
		if (memberRepository.existsByEmailOrNameOrBusinessNumber(email, name, businessNumber)) {
			throw new GlobalException(MemberErrorCode.ALREADY_EXIST_INFORMATION);
		}
	}

	public Member findByEmailAndEmployeeNumber(String email, String employeeNumber) {
		Member admin = memberRepository.findByEmailAndEmployeeNumber(email, employeeNumber);

		if (admin == null) {
			throw new GlobalException(MemberErrorCode.NOT_EXIST);
		}

		if (admin.getRole() != Role.OPERATION && admin.getRole() != Role.OPERATION_LEADER) {
			throw new GlobalException(MemberErrorCode.ACCESS_DENIED);
		}

		return admin;
	}
}