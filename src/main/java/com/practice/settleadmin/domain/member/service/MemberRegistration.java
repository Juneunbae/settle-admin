package com.practice.settleadmin.domain.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.settleadmin.domain.member.Member;
import com.practice.settleadmin.infrastructure.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberRegistration {
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;

	public Member register(String email, String password, String name) {
		String encoded = passwordEncoder.encode(password);
		Member member = Member.create(email, encoded, name);
		return memberRepository.save(member);
	}

	public Member adminRegister(String email, String password, String name, String employeeNumber) {
		String encoded = passwordEncoder.encode(password);
		Member member = Member.adminCreate(email, encoded, name, employeeNumber);
		return memberRepository.save(member);
	}
}