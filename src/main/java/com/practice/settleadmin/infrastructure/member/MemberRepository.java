package com.practice.settleadmin.infrastructure.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.settleadmin.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Boolean existsByEmail(String email);

	Boolean existsByEmailOrEmployeeNumber(String email, String employeeNumber);

	Boolean existsByName(String name);

	Boolean existsByBusinessNumber(String businessNumber);

	Member findByEmailAndEmployeeNumber(String email, String employeeNumber);

	Boolean existsByNameOrBusinessNumber(String name, String businessNumber);

	Boolean existsByEmailOrNameOrBusinessNumber(String email, String name, String businessNumber);
}