package com.practice.settleadmin.application.dto.mapper.member;

import org.mapstruct.Mapper;

import com.practice.settleadmin.application.dto.response.member.GeneralSignUpResponseServiceDto;
import com.practice.settleadmin.domain.member.Member;

@Mapper(componentModel = "spring")
public interface MemberApplicationMapper {
	GeneralSignUpResponseServiceDto toGeneralSignUpResponseServiceDto(Member member);
}