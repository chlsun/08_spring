package com.kh.spring.member.model.service;


import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.member.model.dao.MemberDAO;
import com.kh.spring.member.model.dao.MemberMapper;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	// @Autowired
	// private final MemberDAO memberDao;
	// private final SqlSessionTemplate sqlSession;
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;
	private final MemberValidator validator;
	
	/*
	@Autowired
	public MemberServiceImpl(MemberDAO memberDao, SqlSessionTemplate sqlSession) {
		this.memberDao = memberDao;
		this.sqlSession = sqlSession;
	}
	*/
	
	@Override
	public MemberDTO login(MemberDTO member) {
		
		// 로그인이라는 요청을 처리해줘야하는데
		
		// 아이디가 10자가 넘으면 안됨
		// 아이디/비밀번호 : 빈문자열 또는 null이면 안됨
		
		// 1. Table에 아이디가 존재해야겠다
		// 2. 비밀번호가 일치해야겠다
		// 3. 둘다 통과면 정상적으로 조회할 수 있도록 해주어야겠다.
		/*
		 * SqlSession sqlSession = getSqlSession();
		 * MemberDTO loginMember = new MemberDAO().login(sqlSession, member);
		 * sqlSession.close();
		 * return loginMember;
		 */
		
		
		// 1. loginMember가 null값과 동일하다면 아이디가 존재하지 않는다.
		validator.validatedLoginMember(member);
		
		
		
		// 2. 아이디만 가지고 조회를 하기 때문에
		// 비밀번호를 검증 후
		// 비밀번호가 유효하다면 회원의정보를 session에 담기
		// ?
		
		MemberDTO loginMember = validator.validateMemberExists(member);
		validator.passwordMisMatch(member, loginMember);
		
		return loginMember;
		
		
	}

	public void join(MemberDTO member) {
		
		validator.validatedJoinMember(member);
		
		/*
		int result = memberDao.checkId(sqlSession, member.getMemberId());
		if(result > 0) { return; }
		*/
		
		// memberDao.join(sqlSession, member);
		// 비밀번호는 평문(plain)그대로 저장 X -> 암호화하여 저장
		
		// 암호화 하는법 == .encode()호출
		member.setMemberPw(passwordEncoder.encode(member.getMemberPw()));
		memberMapper.join(member);
	}

	@Override
	public void update(MemberDTO member, HttpSession session) {
		MemberDTO sessionMember = ((MemberDTO)session.getAttribute("loginMember"));
		
		// 사용자 검증
		if(!member.getMemberId().equals(sessionMember.getMemberId())) {
			throw new AuthenticationException("권한없는 접근입니다.");
		}
		// 존재하는 아이디인지 검증
		validator.validateMemberExists(member);
		
		int result = memberMapper.update(member);
		// SQL문 수행 결과 검증
		if(result != 1) {
			throw new AuthenticationException("문제가 일어남 다시 시도해주세요");
		}
		
		sessionMember.setMemberName(member.getMemberName());
		sessionMember.setEmail(member.getEmail());
	}


	@Override
	public int delete(MemberDTO member) {
		return 0;
	}

	@Override
	public String idCheck(String memberId) {
		
		return memberMapper.idCheck(memberId) != null ? "NNNNY" : "NNNNN";
	}

}
