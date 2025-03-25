package com.kh.spring.member.model.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.spring.member.model.dto.MemberDTO;

@Mapper
public interface MemberMapper {
	
	@Select("SELECT MEMBER_ID memberId, MEMBER_PW memberPw, MEMBER_NAME memberName, EMAIL, ENROLL_DATE enrollDate FROM KH_MEMBER WHERE MEMBER_ID = #{memberId}")
	MemberDTO login(MemberDTO member);
	
	@Insert("INSERT INTO KH_MEMBER VALUES(#{memberId}, #{memberPw}, #{memberName}, #{email}, DEFAULT)")
	int join(MemberDTO member);
	
	@Update("UPDATE KH_MEMBER SET MEMBER_NAME = #{memberName}, EMAIL = #{email} WHERE MEMBER_ID = #{memberId}")
	int update(MemberDTO member);
	
	int delete(MemberDTO member);
	
	@Select("SELECT MEMBER_ID FROM KH_MEMBER WHERE MEMBER_ID = #{memberId}")
	String idCheck(String memberId);

}
