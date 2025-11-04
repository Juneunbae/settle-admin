package com.practice.settleadmin.application.dto.mapper.admin;

import org.mapstruct.Mapper;

import com.practice.settleadmin.application.dto.response.admin.AdminCreateStoreOwnerResServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminPromotionResponseServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminSignUpResponseServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminUpdateStoreOwnerResServiceDto;
import com.practice.settleadmin.domain.member.Member;

@Mapper(componentModel = "spring")
public interface AdminApplicationMapper {
	AdminSignUpResponseServiceDto toAdminSignUpResponseServiceDto(Member member);

	AdminPromotionResponseServiceDto toAdminPromotionResponseServiceDto(Member admin, String message);

	AdminCreateStoreOwnerResServiceDto toAdminCreateStoreOwnerResServiceDto(Member member);

	AdminUpdateStoreOwnerResServiceDto toAdminUpdateStoreOwnerResServiceDto(Member member);
}