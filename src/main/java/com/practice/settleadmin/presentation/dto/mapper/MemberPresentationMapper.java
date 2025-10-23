package com.practice.settleadmin.presentation.dto.mapper;

import org.mapstruct.Mapper;

import com.practice.settleadmin.application.dto.request.member.GeneralSignUpRequestServiceDto;
import com.practice.settleadmin.application.dto.response.member.GeneralSignUpResponseServiceDto;
import com.practice.settleadmin.presentation.dto.request.member.GeneralSignUpRequestDto;
import com.practice.settleadmin.presentation.dto.response.member.GeneralSignUpResponseDto;

@Mapper(componentModel = "spring")
public interface MemberPresentationMapper {
	GeneralSignUpRequestServiceDto toGeneralSignUpServiceDto(GeneralSignUpRequestDto request);

	GeneralSignUpResponseDto toGeneralSignUpResponseDto(GeneralSignUpResponseServiceDto request);
}