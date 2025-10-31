package com.practice.settleadmin.presentation.controller.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.settleadmin.application.dto.request.admin.AdminPromotionRequestServiceDto;
import com.practice.settleadmin.application.dto.request.admin.AdminSignUpRequestServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminPromotionResponseServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminSignUpResponseServiceDto;
import com.practice.settleadmin.application.service.admin.AdminService;
import com.practice.settleadmin.presentation.dto.mapper.AdminPresentationMapper;
import com.practice.settleadmin.presentation.dto.request.admin.AdminPromotionRequestDto;
import com.practice.settleadmin.presentation.dto.request.admin.AdminSignUpRequestDto;
import com.practice.settleadmin.presentation.dto.response.admin.AdminPromotionResponseDto;
import com.practice.settleadmin.presentation.dto.response.admin.AdminSignUpResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
	private final AdminService adminService;
	private final AdminPresentationMapper mapper;

	@PostMapping("/sign-up")
	public ResponseEntity<AdminSignUpResponseDto> signup(@Valid @RequestBody AdminSignUpRequestDto request) {
		AdminSignUpRequestServiceDto serviceDto = mapper.toAdminSignUpRequestServiceDto(request);

		AdminSignUpResponseServiceDto serviceResponse = adminService.signup(serviceDto);

		AdminSignUpResponseDto responseDto = mapper.toAdminSignUpResponseDto(serviceResponse);

		return ResponseEntity.ok(responseDto);
	}

	@PostMapping("/promotions")
	public ResponseEntity<AdminPromotionResponseDto> promoteToAdmin(@RequestBody AdminPromotionRequestDto request) {
		AdminPromotionRequestServiceDto serviceDto = mapper.toAdminPromotionRequestServiceDto(request);

		AdminPromotionResponseServiceDto serviceResponse = adminService.promoteToAdmin(serviceDto);

		AdminPromotionResponseDto responseDto = mapper.toAdminPromotionResponseDto(serviceResponse);

		return ResponseEntity.ok(responseDto);
	}
}