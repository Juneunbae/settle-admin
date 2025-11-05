package com.practice.settleadmin.presentation.dto.mapper;

import org.mapstruct.Mapper;

import com.practice.settleadmin.application.dto.request.admin.AdminCreateStoreOwnerReqServiceDto;
import com.practice.settleadmin.application.dto.request.admin.AdminDeleteStoreOwnerReqServiceDto;
import com.practice.settleadmin.application.dto.request.admin.AdminPromotionRequestServiceDto;
import com.practice.settleadmin.application.dto.request.admin.AdminSignUpRequestServiceDto;
import com.practice.settleadmin.application.dto.request.admin.AdminUpdateStoreOwnerReqServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminCreateStoreOwnerResServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminDeleteStoreOwnerResServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminPromotionResponseServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminSignUpResponseServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminUpdateStoreOwnerResServiceDto;
import com.practice.settleadmin.presentation.dto.request.admin.AdminCreateStoreOwnerReqDto;
import com.practice.settleadmin.presentation.dto.request.admin.AdminDeleteStoreOwnerReqDto;
import com.practice.settleadmin.presentation.dto.request.admin.AdminPromotionRequestDto;
import com.practice.settleadmin.presentation.dto.request.admin.AdminSignUpRequestDto;
import com.practice.settleadmin.presentation.dto.request.admin.AdminUpdateStoreOwnerReqDto;
import com.practice.settleadmin.presentation.dto.response.admin.AdminCreateStoreOwnerResDto;
import com.practice.settleadmin.presentation.dto.response.admin.AdminDeleteStoreOwnerResDto;
import com.practice.settleadmin.presentation.dto.response.admin.AdminPromotionResponseDto;
import com.practice.settleadmin.presentation.dto.response.admin.AdminSignUpResponseDto;
import com.practice.settleadmin.presentation.dto.response.admin.AdminUpdateStoreOwnerResDto;

@Mapper(componentModel = "spring")
public interface AdminPresentationMapper {
	AdminSignUpRequestServiceDto toAdminSignUpRequestServiceDto(AdminSignUpRequestDto request);

	AdminSignUpResponseDto toAdminSignUpResponseDto(AdminSignUpResponseServiceDto request);

	AdminPromotionRequestServiceDto toAdminPromotionRequestServiceDto(AdminPromotionRequestDto request);

	AdminPromotionResponseDto toAdminPromotionResponseDto(AdminPromotionResponseServiceDto request);

	AdminCreateStoreOwnerReqServiceDto toAdminCreateStoreOwnerReqServiceDto(AdminCreateStoreOwnerReqDto request);

	AdminCreateStoreOwnerResDto toAdminCreateStoreOwnerResDto(AdminCreateStoreOwnerResServiceDto request);

	AdminUpdateStoreOwnerReqServiceDto toAdminUpdateStoreOwnerReqServiceDto(
		Long storeOwnerId, AdminUpdateStoreOwnerReqDto request
	);

	AdminUpdateStoreOwnerResDto toAdminUpdateStoreOwnerResDto(AdminUpdateStoreOwnerResServiceDto request);

	AdminDeleteStoreOwnerReqServiceDto toAdminDeleteStoreOwnerReqServiceDto(
		Long id, AdminDeleteStoreOwnerReqDto request
	);

	AdminDeleteStoreOwnerResDto toAdminDeleteStoreOwnerResDto(AdminDeleteStoreOwnerResServiceDto request);
}