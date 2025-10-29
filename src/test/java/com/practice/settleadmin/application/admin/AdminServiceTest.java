package com.practice.settleadmin.application.admin;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.practice.settleadmin.application.dto.mapper.admin.AdminApplicationMapper;
import com.practice.settleadmin.application.dto.request.admin.AdminSignUpRequestServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminSignUpResponseServiceDto;
import com.practice.settleadmin.application.service.admin.AdminService;
import com.practice.settleadmin.domain.member.Member;
import com.practice.settleadmin.domain.member.service.MemberRegistration;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
	@Mock
	private AdminApplicationMapper mapper;

	@Mock
	private MemberRegistration memberRegistration;

	@InjectMocks
	private AdminService adminService;

	@Test
	@DisplayName("관리자 가입 성공")
	void signup_success() {
		// given
		AdminSignUpRequestServiceDto request = new AdminSignUpRequestServiceDto(
			"admin@test.com",
			"Password1!",
			"관리자 테스트1",
			"25-00000001"
		);

		Member newAdmin = Member.adminCreate(
			request.email(),
			"encoded-password",
			request.name(),
			request.employeeNumber()
		);

		AdminSignUpResponseServiceDto expectedResponse = new AdminSignUpResponseServiceDto(
			"admin@test.com",
			"관리자 테스트1",
			"25-00000001",
			LocalDateTime.now()
		);

		given(memberRegistration.adminRegister(
			request.email(),
			request.password(),
			request.name(),
			request.employeeNumber()
		)).willReturn(newAdmin);

		given(mapper.toAdminSignUpResponseServiceDto(newAdmin)).willReturn(expectedResponse);

		// when
		AdminSignUpResponseServiceDto actualResponse = adminService.signup(request);

		// then
		// memberRegistration이 정확한 인자로 호출됐는지 확인
		verify(memberRegistration, times(1)).adminRegister(
			request.email(),
			request.password(),
			request.name(),
			request.employeeNumber()
		);

		// mapper가 newAdmin으로 호출됐는지 확인
		verify(mapper, times(1)).toAdminSignUpResponseServiceDto(newAdmin);

		// 반환 값이 우리가 기대한 값인지 확인
		assertThat(actualResponse).isNotNull();
		assertThat(actualResponse.email()).isEqualTo(expectedResponse.email());
		assertThat(actualResponse.name()).isEqualTo(expectedResponse.name());
		assertThat(actualResponse.employeeNumber()).isEqualTo(expectedResponse.employeeNumber());
	}

	@Test
	@DisplayName("관리자 가입 실패 - 이미 존재하는 이메일")
	void signup_fail_existEmail() {
		// given
		AdminSignUpRequestServiceDto request = new AdminSignUpRequestServiceDto(
			"admin@test.com",
			"Password1!",
			"중복된 관리자",
			"25-00000001"
		);

		RuntimeException exception = new RuntimeException("이미 존재하는 계정입니다.");

		given(memberRegistration.adminRegister(
			request.email(),
			request.password(),
			request.name(),
			request.employeeNumber()
		)).willThrow(exception);

		// when
		RuntimeException thrown = assertThrows(RuntimeException.class, () -> adminService.signup(request));

		// then
		assertThat(thrown).hasMessageContaining("이미 존재하는 계정입니다.");

		verify(mapper, times(0)).toAdminSignUpResponseServiceDto(any());
	}
}