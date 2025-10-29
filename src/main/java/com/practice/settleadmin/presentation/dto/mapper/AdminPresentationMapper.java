package com.practice.settleadmin.presentation.dto.mapper;

import org.mapstruct.Mapper;

import com.practice.settleadmin.application.dto.request.admin.AdminSignUpRequestServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminSignUpResponseServiceDto;
import com.practice.settleadmin.presentation.dto.request.admin.AdminSignUpRequestDto;
import com.practice.settleadmin.presentation.dto.response.admin.AdminSignUpResponseDto;

@Mapper(componentModel = "spring")
public interface AdminPresentationMapper {
	AdminSignUpRequestServiceDto toAdminSignUpRequestServiceDto(AdminSignUpRequestDto request);

	AdminSignUpResponseDto toAdminSignUpResponseDto(AdminSignUpResponseServiceDto request);
}