package com.practice.settleadmin.domain.member;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.practice.settleadmin.domain.member.service.MemberRegistration;
import com.practice.settleadmin.exception.GlobalException;
import com.practice.settleadmin.exception.member.MemberErrorCode;
import com.practice.settleadmin.infrastructure.member.MemberRepository;

@ExtendWith(MockitoExtension.class)
public class MemberRegistrationTest {
	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private MemberRepository memberRepository;

	@InjectMocks
	private MemberRegistration memberRegistration;

	@Test
	@DisplayName(value = "멤버 저장 성공 테스트")
	void successRegister() {
		// given
		String email = "test@test.com";
		String password = "test123!";
		String name = "테스트 유저";
		String encoded = "$2a$10$encoded";

		given(memberRepository.existsByEmail(email)).willReturn(false);
		given(passwordEncoder.encode(password)).willReturn(encoded);
		given(memberRepository.save(any(Member.class)))
			.willAnswer(invocation -> invocation.getArgument(0));

		// when
		Member result = memberRegistration.register(email, password, name);

		// then
		then(memberRepository).should().existsByEmail(email);
		then(passwordEncoder).should().encode(password);
		then(memberRepository).should().save(any(Member.class));

		assertThat(result).isNotNull();
		assertThat(result.getEmail()).isEqualTo(email);
		assertThat(result.getPassword()).isEqualTo(encoded);
		assertThat(result.getName()).isEqualTo(name);
		assertThat(result.getRole()).isEqualTo(Role.GENERAL);
		assertThat(result.getStatus()).isEqualTo(Status.ACTIVATE);
	}

	@Test
	@DisplayName("중복 이메일이면 GlobalException을 던지고 save/encode가 호출되지 않는다")
	void register_fail_whenEmailDuplicated() {
		// given
		String email = "dup@test.com";
		String raw = "Abcd1234!";
		String name = "테스트";

		given(memberRepository.existsByEmail(email)).willReturn(true);

		// when
		Throwable thrown = catchThrowable(() ->
			memberRegistration.register(email, raw, name)
		);

		// then
		assertThat(thrown)
			.isInstanceOf(GlobalException.class)
			.hasMessageContaining(MemberErrorCode.ALREADY_EXIST_EMAIL.getMessage());

		then(memberRepository).should().existsByEmail(email);
		then(passwordEncoder).shouldHaveNoInteractions(); // encode 호출 X
		then(memberRepository).should(never()).save(any(Member.class)); // save 호출 X
	}
}