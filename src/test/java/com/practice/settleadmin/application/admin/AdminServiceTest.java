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
import com.practice.settleadmin.application.dto.request.admin.AdminPromotionRequestServiceDto;
import com.practice.settleadmin.application.dto.request.admin.AdminSignUpRequestServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminPromotionResponseServiceDto;
import com.practice.settleadmin.application.dto.response.admin.AdminSignUpResponseServiceDto;
import com.practice.settleadmin.application.service.admin.AdminService;
import com.practice.settleadmin.domain.member.Member;
import com.practice.settleadmin.domain.member.Role;
import com.practice.settleadmin.domain.member.Status;
import com.practice.settleadmin.domain.member.service.MemberRegistration;
import com.practice.settleadmin.domain.member.service.PromoteToAdmin;
import com.practice.settleadmin.exception.GlobalException;
import com.practice.settleadmin.exception.member.MemberErrorCode;
import com.practice.settleadmin.infrastructure.member.MemberRepository;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
	@Mock
	private AdminApplicationMapper mapper;

	@Mock
	private MemberRegistration memberRegistration;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private PromoteToAdmin promoteToAdmin;

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

	@Test
	@DisplayName("관리자 코드 승인 완료 - Role 관리자로 업데이트")
	void promote_success_to_admin() {
		String email = "operation@test.com";
		String employeeNumber = "25-00000001";
		String message = "관리자 승인이 완료되었습니다.";
		String promotionCode = "promotionCode";

		Member operationMember = Member.builder()
			.id(1L)
			.email(email)
			.name("테스트 계정")
			.employeeNumber(employeeNumber)
			.status(Status.ACTIVATE)
			.role(Role.OPERATION)
			.createdAt(LocalDateTime.now().minusDays(1))
			.build();

		Member admin = Member.builder()
			.id(1L)
			.email(email)
			.name("테스트 계정")
			.employeeNumber(employeeNumber)
			.status(Status.ACTIVATE)
			.role(Role.ADMIN)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();

		// given
		AdminPromotionRequestServiceDto request = new AdminPromotionRequestServiceDto(
			email,
			employeeNumber,
			"promotionCode"
		);

		AdminPromotionResponseServiceDto response = new AdminPromotionResponseServiceDto(
			email,
			message,
			LocalDateTime.now()
		);

		given(memberRepository.findByEmailAndEmployeeNumber(request.email(), request.employeeNumber()))
			.willReturn(operationMember);
		given(promoteToAdmin.promote(operationMember, promotionCode)).willReturn(admin);
		given(mapper.toAdminPromotionResponseServiceDto(admin, message)).willReturn(response);

		// when
		AdminPromotionResponseServiceDto actualResponse = adminService.promoteToAdmin(request);

		// then
		assertThat(actualResponse).isNotNull();
		assertThat(actualResponse.email()).isEqualTo(response.email());
		assertThat(actualResponse.message()).contains(message);

		verify(memberRepository).findByEmailAndEmployeeNumber(request.email(), request.employeeNumber());
		verify(promoteToAdmin, times(1)).promote(operationMember, request.promotionCode());
		verify(mapper, times(1)).toAdminPromotionResponseServiceDto(admin, message);
	}

	@Test
	@DisplayName("관리자 승인 실패 - 잘못된 승인번호")
	void promote_fail_incorrectPromotionCode() {
		String email = "operation@test.com";
		String employeeNumber = "25-00000001";
		String badCode = "badCode";

		Member operationMember = Member.builder()
			.id(1L)
			.email(email)
			.name("테스트 계정")
			.employeeNumber(employeeNumber)
			.status(Status.ACTIVATE)
			.role(Role.OPERATION)
			.createdAt(LocalDateTime.now().minusDays(1))
			.build();

		// given
		AdminPromotionRequestServiceDto request = new AdminPromotionRequestServiceDto(
			email,
			employeeNumber,
			badCode
		);

		given(memberRepository.findByEmailAndEmployeeNumber(request.email(), request.employeeNumber()))
			.willReturn(operationMember);
		given(promoteToAdmin.promote(operationMember, badCode))
			.willThrow(new GlobalException(MemberErrorCode.INVALID_CODE));

		// when & then
		assertThatThrownBy(() -> adminService.promoteToAdmin(request))
			.isInstanceOf(GlobalException.class)
			.hasMessageContaining(MemberErrorCode.INVALID_CODE.getMessage());

		then(memberRepository).should(times(1)).findByEmailAndEmployeeNumber(
			request.email(), request.employeeNumber()
		);
		then(promoteToAdmin).should(times(1)).promote(operationMember, badCode);
		then(mapper).shouldHaveNoInteractions();
	}

	@Test
	@DisplayName("관리자 승인 실패 - 권한 없는 유저 요청")
	void promote_fail_accessDenied() {
		String email = "general@test.com";
		String employeeNumber = "25-00000099";
		String promotionCode = "ANY_CODE";

		Member generalMember = Member.builder()
			.id(99L)
			.email(email)
			.name("테스트 계정")
			.employeeNumber(employeeNumber)
			.status(Status.ACTIVATE)
			.role(Role.GENERAL)
			.createdAt(LocalDateTime.now().minusDays(1))
			.build();

		// given
		AdminPromotionRequestServiceDto request = new AdminPromotionRequestServiceDto(
			email,
			employeeNumber,
			promotionCode
		);

		given(memberRepository.findByEmailAndEmployeeNumber(request.email(), request.employeeNumber()))
			.willReturn(generalMember);

		// when & then
		assertThatThrownBy(() -> adminService.promoteToAdmin(request))
			.isInstanceOf(GlobalException.class)
			.hasMessageContaining(MemberErrorCode.ACCESS_DENIED.getMessage());

		then(memberRepository).should(times(1)).findByEmailAndEmployeeNumber(
			request.email(), request.employeeNumber()
		);
		then(promoteToAdmin).shouldHaveNoInteractions();
		then(mapper).shouldHaveNoInteractions();
	}
}