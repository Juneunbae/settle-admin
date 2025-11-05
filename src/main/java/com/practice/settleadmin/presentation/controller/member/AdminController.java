package com.practice.settleadmin.presentation.controller.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.settleadmin.application.dto.request.admin.AdminCreateStoreOwnerReqServiceDto;
import com.practice.settleadmin.application.dto.request.admin.AdminPromotionRequestServiceDto;
import com.practice.settleadmin.application.dto.request.admin.AdminSignUpRequestServiceDto;
import com.practice.settleadmin.application.dto.request.admin.AdminUpdateStoreOwnerReqServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminCreateStoreOwnerResServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminPromotionResponseServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminSignUpResponseServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminUpdateStoreOwnerResServiceDto;
import com.practice.settleadmin.application.service.admin.AdminService;
import com.practice.settleadmin.presentation.dto.mapper.AdminPresentationMapper;
import com.practice.settleadmin.presentation.dto.request.admin.AdminCreateStoreOwnerReqDto;
import com.practice.settleadmin.presentation.dto.request.admin.AdminPromotionRequestDto;
import com.practice.settleadmin.presentation.dto.request.admin.AdminSignUpRequestDto;
import com.practice.settleadmin.presentation.dto.request.admin.AdminUpdateStoreOwnerReqDto;
import com.practice.settleadmin.presentation.dto.response.admin.AdminCreateStoreOwnerResDto;
import com.practice.settleadmin.presentation.dto.response.admin.AdminPromotionResponseDto;
import com.practice.settleadmin.presentation.dto.response.admin.AdminSignUpResponseDto;
import com.practice.settleadmin.presentation.dto.response.admin.AdminUpdateStoreOwnerResDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "관리자")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
	private final AdminService adminService;
	private final AdminPresentationMapper mapper;

	@PostMapping("/sign-up")
	@Operation(summary = "운영팀 가입하기", description = "운영팀 가입을 위한 API")
	public ResponseEntity<AdminSignUpResponseDto> signup(@Valid @RequestBody AdminSignUpRequestDto request) {
		AdminSignUpRequestServiceDto serviceDto = mapper.toAdminSignUpRequestServiceDto(request);

		AdminSignUpResponseServiceDto serviceResponse = adminService.signup(serviceDto);

		AdminSignUpResponseDto responseDto = mapper.toAdminSignUpResponseDto(serviceResponse);

		return ResponseEntity.ok(responseDto);
	}

	@PostMapping("/promotions")
	@Operation(summary = "관리자 승급하기", description = "관리자 승급을 위한 API")
	public ResponseEntity<AdminPromotionResponseDto> promoteToAdmin(@RequestBody AdminPromotionRequestDto request) {
		AdminPromotionRequestServiceDto serviceDto = mapper.toAdminPromotionRequestServiceDto(request);

		AdminPromotionResponseServiceDto serviceResponse = adminService.promoteToAdmin(serviceDto);

		AdminPromotionResponseDto responseDto = mapper.toAdminPromotionResponseDto(serviceResponse);

		return ResponseEntity.ok(responseDto);
	}

	@PostMapping("/store-owners")
	@Operation(summary = "점주 생성하기", description = "점주 생성을 위한 API")
	public ResponseEntity<AdminCreateStoreOwnerResDto> createStoreOwners(@RequestBody AdminCreateStoreOwnerReqDto req) {
		AdminCreateStoreOwnerReqServiceDto serviceDto = mapper.toAdminCreateStoreOwnerReqServiceDto(req);

		AdminCreateStoreOwnerResServiceDto serviceResponse = adminService.createStoreOwners(serviceDto);

		AdminCreateStoreOwnerResDto responseDto = mapper.toAdminCreateStoreOwnerResDto(serviceResponse);

		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/store-owners/{id}")
	@Operation(summary = "점주 정보 수정하기", description = "점주 수정을 위한 API")
	public ResponseEntity<AdminUpdateStoreOwnerResDto> updateStoreOwners(
		@PathVariable Long id, @RequestBody AdminUpdateStoreOwnerReqDto req
	) {
		AdminUpdateStoreOwnerReqServiceDto serviceDto = mapper.toAdminUpdateStoreOwnerReqServiceDto(id, req);

		AdminUpdateStoreOwnerResServiceDto serviceResponse = adminService.updateStoreOwners(serviceDto);

		AdminUpdateStoreOwnerResDto responseDto = mapper.toAdminUpdateStoreOwnerResDto(serviceResponse);

		return ResponseEntity.ok(responseDto);
	}
}