package com.kh.spring.board.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.exception.InvalidParameterException;
import com.kh.spring.reply.model.dto.ReplyDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
// @RequestMapping("boards")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	
	
	// ?page=1
	@GetMapping("boards")
	public String goToBoardList(@RequestParam(name="page", defaultValue="1") int page,
								Model model){
		
		// 한페이지에 보여질 게시글 == 5
		// 화면에 페이지 버튼 갯수 == 5
		if(page < 1) {
			throw new InvalidParameterException("페이지 갯수 1 미만");
		}
		
		Map<String, Object> map = boardService.selectBoardList(page);
		
		model.addAttribute("map", map);
		
		return "board/board_list";
	}
	@GetMapping("form.bo")
	public String goToForm() {
		return "board/insert_form";
	}
	
	
	@PostMapping("boards")
	public ModelAndView newBoard(ModelAndView mv,
								 BoardDTO board,
								 MultipartFile upfile,
								 HttpSession session) {
		
		log.info("게시글정보 : {}, 파일정보 : {}", board, upfile);
		
		// 첨부파일의 존재유무
		// => 차이점 => MultipartFile타입의 filename필드값으로 확인을 하겠다
		
		// INSERT INTO TB_SPTING_BOARD(BOARD_TITLE, BOARD_CONTENT, BOARD_WRITER, CHANGE_NAME)
		//				VALUES(#{boardTitle}, #{boardContent}, #{boardWriter}, #{changeName});
		
		// 1. 권한있는 요청인가
		// 2. 값들이 유효성이 있는 값인가
		// 3. 전달된 파일의 존재할 경우 => 파일명 수정 서버에 올리고 BoardDTO의 changeName필드에 값을 대입
		
		boardService.insertBoard(board, upfile, session);
		
		session.setAttribute("message", "게시글 등록 성공");
		
		mv.setViewName("redirect:boards");
		return mv;
	}
	
	@GetMapping("boards/{id}")
	public ModelAndView goToDetail(@PathVariable(name="id") int boardNo,
								   ModelAndView mv) {
		
		log.info("{}", boardNo);
		
		if(boardNo < 1) {
			throw new InvalidParameterException("비정상적인 접근입니다.");
		}
		
		BoardDTO board = boardService.selectBoard(boardNo);
		mv.addObject("board", board).setViewName("board/board_detail");
		
		return mv;
	}
	
	@GetMapping("search")
	public ModelAndView doSearch(@RequestParam(name="condition") String condition,
								 @RequestParam(name="keyword") String keyword,
								 @RequestParam(name="page", defaultValue="1") int page,
								 ModelAndView mv) {
		
		Map<String, String> map = new HashMap();
		map.put("condition", condition);
		map.put("keyword", keyword);
		map.put("currentPage",  String.valueOf(page));
		
		Map<String, Object> list = boardService.doSearch(map);
		list.put("condition", condition);
		list.put("keyword", keyword);
		
		mv.addObject("map", list).setViewName("board/board_list");
		
		return mv;
	}
	
	@ResponseBody
	@PostMapping("reply")
	public String insertReply(ReplyDTO reply, HttpSession session) {
		
		log.info("{}", reply);
		
		return String.valueOf(boardService.insertReply(reply, session));
	}
	
	
	
}
