package com.kh.spring.member.model.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.kh.spring.member.model.dto.MemberDTO;


@Component
public interface MemberService {
		// 로그인
		MemberDTO login(MemberDTO member);
		
		// 회원가입
		// 좋은방법 : 가입된 회원의 정보를 반환해준다.(Hibernate)
		// 일반적인 방법 : 정수값을 반환하거나 값을 반환하지 않는다. (MyBatis)
		void join(MemberDTO member);
		
		// 회원정보수정
		void update(MemberDTO member, HttpSession session);
		
		// 회원탈퇴
		int delete(MemberDTO member);
		
		//아이디 중복체크
		String idCheck(String memberId);
		
		// 이메일인증
}