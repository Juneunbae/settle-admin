package com.practice.settleadmin.domain.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.settleadmin.domain.member.Member;
import com.practice.settleadmin.exception.GlobalException;
import com.practice.settleadmin.exception.member.MemberErrorCode;
import com.practice.settleadmin.infrastructure.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberRegistration {
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;

	public Member register(String email, String password, String name) {
		existsByEmail(email);
		String encoded = passwordEncoder.encode(password);
		Member member = Member.create(email, encoded, name);
		return memberRepository.save(member);
	}

	public Member adminRegister(String email, String password, String name, String employeeNumber) {
		existsByEmailOrEmployeeNumber(email, employeeNumber);
		String encoded = passwordEncoder.encode(password);
		Member member = Member.adminCreate(email, encoded, name, employeeNumber);
		return memberRepository.save(member);
	}

	public void existsByEmail(String email) {
		if (memberRepository.existsByEmail(email)) {
			throw new GlobalException(MemberErrorCode.ALREADY_EXIST_EMAIL);
		}
	}

	public void existsByEmailOrEmployeeNumber(String email, String employeeNumber) {
		if (memberRepository.existsByEmailOrEmployeeNumber(email, employeeNumber)) {
			throw new GlobalException(MemberErrorCode.ALREADY_EXIST_EMAIL_OR_EMPLOYEE_NUMBER);
		}
	}
}