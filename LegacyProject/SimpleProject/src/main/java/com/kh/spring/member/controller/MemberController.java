package com.kh.spring.member.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.service.MemberServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


// RequestHandler라고 불리는 메서드들이 들어가는곳

// @Component
@Slf4j
@Controller
@RequiredArgsConstructor // lombok 어노테이션)의존성 주입 생성자를 생성해주는 애노테이션 (final이 붙은 필드를 자동으로 생성자로 만들어줌)
public class MemberController {
	
	// @Autowired //1번
	private final MemberService memberService;
	
//	@Autowired // 2번
//	public void setMemberService(MemberService memberService) {
//		this.memberService = memberService;
//	}
	
	/*
	@Autowired // 3번 (좋은방법 하지만 1번이 더 편함 그래서 lombok의 @RequiredArgsConstructor << 이런 애노테이션 사용)
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	*/
	
//	@RequestMapping(value="login")
//	public String login(HttpServletRequest request) {
//		// System.out.println("로그인 요청 확인");
//		
//		String id = request.getParameter("id");
//		String pw = request.getParameter("pw");
//		
//		//System.out.printf("id : %s, pw : $s", id, pw);
//		log.info("id : {}, pw : {}", id, pw);
//		
//		return "main_page";
//	}

	
//	@RequestMapping("login")
//	public String login(@RequestParam(value="id", defaultValue="abcde") String id,
//						@RequestParam(value="pw") String pw){
//		log.info("이렇게도 넘어오나요? id {} / pw : {}",id, pw);
//		
//		return "main_page";
//	}
	
	
	// 밑에 방법이 좋긴하지만 매개변수에 @RequestParam를 넣어주는걸 권장
//	@PostMapping("login")
//	public String login(String id, String pw) {
//	
//		log.info("id : {}, pw : {}", id, pw);
//		
//		MemberDTO member = new MemberDTO();
//		
//		member.setMemberId(id);
//		member.setMemberPw(pw);
//		
//		return "main_page";
//	}
	
	
	/**
	 * 커맨드 객체 방식
	 * 
	 * 1. 매개변수 자료형에 반드시 기본생성자가 존재할 것
	 * 2. 전달되는 키값과 객체의 필드명이 동일할 것
	 * 3. setter메서드가 반드시 존재할것
	 * 
	 * 스프링에서 해당 객체를 기본생성자를 통해서 생성한 후 내부적으로 setter메서드를 찾아서
	 * 요청 시 전달값을 해당 필드에 대입해줌
	 * (=> Setter Injection)
	 */
	

//	// input name속성값(key값)과 담으려는 DTO의 필드명을 일치하게 해줘야함
//	@PostMapping("login")
//	public String login(MemberDTO member, 
//						HttpSession session,
//						Model model){
//		
////		log.info("{}", member);
//		
//		/* 데이티가공 => 스프링이 대신 해줘서 매개변수로 받음
//		 * 요청처리 => 
//		 * 응답화면 지정
//		 */
//		
//		MemberDTO loginMember = memberService.login(member);
//		
//		if(loginMember != null) { // 성공했을 때
//			// sessionScope에 로그인정보를 담아줌
//			session.setAttribute("loginMember", loginMember);
//			
//			// sendRedirect
//			return "redirect:/";
//			
//		} else { // 실패했을 때
//			
//			// requestScope에 에러문구를 담아서 포워딩
//			// Spring에서는 Model객체를 이용해서 RequestScope에 값을 담음
//			model.addAttribute("message", "로그인 실패");
//			return "include/error_page";
//		}
//
//	}
	
	// 두 번째 방법반환타입 ModelAndView로 돌아가기
	@PostMapping("login")
	public ModelAndView login(MemberDTO member, HttpSession session,
							  ModelAndView mv) {
		
		MemberDTO loginMember = memberService.login(member);
		
		if(loginMember != null) {
			session.setAttribute("loginMember", loginMember);
			mv.setViewName("redirect:/");
		}else {
			mv.addObject("message", "??").setViewName("include/error_page");
		}
		
		return mv;
	}
	
	@GetMapping("logout")
	public ModelAndView logout(HttpSession session,
							   ModelAndView mv) {
		
		session.removeAttribute("loginMember");
		mv.setViewName("redirect:/");
		return mv;
	}
	
	@GetMapping("signup-form")
	public String singupForm() {
		return "member/signup-form";
	}
	
	
	/**
	 * 
	 * @param member
	 * @return 성공시 main으로 실패하면 err담아서 errpage로 이동
	 */
	@PostMapping("join")
	public String join(MemberDTO member) {
		/*
		request에서 뽑기전에 해야하는데 매개변수로 MemberDTO에 담긴후에 Encoding됨
		try {
			request.setCharacterEncoding("UTF-8");			
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		*/
//		log.info("멤버 필드 찍어보기 : {}", member);
		memberService.join(member);
		
		return "main_page";
	}
	
	@GetMapping("home")
	public String home() {
		return "main_page";
	}
	
	
	@GetMapping("my-page")
	public String myPage() {
		return "member/my-page";
	}
	
	@PostMapping("update-member")
	public String updateMember(MemberDTO member, HttpSession session) {
		// 1. Controller에서는 RequestMapping 애노테이션 및 요청 시 전달값이 잘 전달되는지 확인
		/*
		 * 1_1) 404 발생 : mapping값 잘못 적음 
		 * 
		 * 1_2) 405 발생 : 앞단에선 POST / GET으로 요청을 보내놓고 메소드와 맞지않은 애노테이션을 사용했을 때
		 * 
		 * 1_3) 필드에 값이 들어오지 않는 경우 
		 */
		log.info("{}", member);
		
		// 2. 이번에 실행할 SQL문을 생각 (원래는 SQL문이 벌써 작성되어있어야함)
		// UPDATE문 			==> KH_MEMBER(MEMBER_ID)
		// ID, PW, NAME, EMAIL, DATE
		// 2_1) 매개변수 MemberDTO타입의 memberId 필드값
		// 2_2) SessionScope에 loginMember 키값에 memberId필드값
		// SessionScope를 넘겨주어야겠구나
		
		// 값들이 유효한 값인지 체크하기
		// MemberID가 존재하는 아이디이닞 체크하기
		
		// UPDATE KH_MEMBER SET MEMBER_NAME = 사용자가 입력한 이름, 
		//							  EMAIL = 사용자가 입력한 이메일
		//					WHERE MEMBER_ID = 사용자가 입력한 아이디
		// UPDATE 수행의 결과 => PK를 조건으로 수행함 => 0 / 1
		// 수행에 성공했을 경우 =>
		// my_page.jsp 로 이동 + 갱신된 회원의 정보 출력
		// ID 가지고 다시 조회 => login메서드 재활용
		
		// 수행에 실패했을 경우 =>
		// message를 담아서 error_page로 포워딩
		// 예외 발생 => 예외처리기로 위임
		
		memberService.update(member, session);
		
		return "redirect:my-page";
	}
	
	@ResponseBody
	@GetMapping("id-check")
	public String idCheck(@RequestParam(name="memberId") String memberId) {
		
		
		
		return memberService.idCheck(memberId);
	}
	
}
