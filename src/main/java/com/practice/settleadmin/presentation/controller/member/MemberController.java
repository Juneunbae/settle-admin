package com.practice.settleadmin.presentation.controller.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.settleadmin.application.dto.request.member.GeneralSignUpRequestServiceDto;
import com.practice.settleadmin.application.dto.response.member.GeneralSignUpResponseServiceDto;
import com.practice.settleadmin.application.service.member.MemberService;
import com.practice.settleadmin.presentation.dto.mapper.MemberPresentationMapper;
import com.practice.settleadmin.presentation.dto.request.member.GeneralSignUpRequestDto;
import com.practice.settleadmin.presentation.dto.response.member.GeneralSignUpResponseDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "멤버")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
	private final MemberPresentationMapper mapper;
	private final MemberService memberService;

	@PostMapping("/sign-up")
	public ResponseEntity<GeneralSignUpResponseDto> signup(@Valid @RequestBody GeneralSignUpRequestDto request) {
		GeneralSignUpRequestServiceDto serviceDto = mapper.toGeneralSignUpServiceDto(request);

		GeneralSignUpResponseServiceDto serviceResponse = memberService.signup(serviceDto);

		GeneralSignUpResponseDto responseDto = mapper.toGeneralSignUpResponseDto(serviceResponse);

		return ResponseEntity.ok(responseDto);
	}
}