package com.kh.spring.member.model.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.kh.spring.exception.DuplicateIdException;
import com.kh.spring.exception.InvalidParameterException;
import com.kh.spring.exception.MemberNotFoundException;
import com.kh.spring.exception.PasswordNotMatchException;
import com.kh.spring.exception.TooLageValueException;
import com.kh.spring.member.model.dao.MemberMapper;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberValidator {
	
	private final PasswordEncoder passwordEncoder;

	private final MemberMapper mapper;

	private void valdatedLength(MemberDTO member) {
		if (member.getMemberId().length() > 10) {
			throw new TooLageValueException("아이디 값이 너무 깁니다. 10자 이내로 작성해주세요.");
		}
	}

	private void validatedValue(MemberDTO member) {
		if (member == null || member.getMemberId() == null || member.getMemberId().trim().isEmpty()
				|| member.getMemberPw() == null || member.getMemberPw().trim().isEmpty()) {
			throw new InvalidParameterException("빈 문자열");
		}
	}
	
	private void validateDuplicateId(MemberDTO member) {
		MemberDTO existingMember = mapper.login(member);
		if(existingMember != null && member.getMemberId().equals(existingMember.getMemberId())) {
			throw new DuplicateIdException("이미 존재하는 회원의 아이디입니다.");
		}
	}
	
	public void passwordMisMatch(MemberDTO member, MemberDTO loginMember) {
		if(!!!passwordEncoder.matched(member.getMemberPw(), loginMember.getMemberPw())) {
			throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
		}
	}
	

	public void validatedLoginMember(MemberDTO member) {
		valdatedLength(member);
		validatedValue(member);
	}

	public void validatedJoinMember(MemberDTO member) {
		validatedLoginMember(member);
		validateDuplicateId(member);
	}

	
	public MemberDTO validateMemberExists(MemberDTO member) {
		MemberDTO loginMember = mapper.login(member);
		if(loginMember != null) {
			return loginMember;
		}
		throw new MemberNotFoundException("존재하지 않는 아이디입니다");
	}
	 
}
