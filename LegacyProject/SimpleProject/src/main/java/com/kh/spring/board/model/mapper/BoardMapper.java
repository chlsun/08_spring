package com.kh.spring.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.reply.model.dto.ReplyDTO;

@Mapper
public interface BoardMapper {
	
	void insertBoard(BoardDTO board);
	
	@Select("SELECT COUNT(*) FROM TB_SPRING_BOARD WHERE STATUS = 'Y'")
	int selectTotalCount();
	
	List<BoardDTO> selectBoardList(RowBounds rowBouds);
	
	BoardDTO selectBoard(int boardNo);
	
	List<ReplyDTO> selectReply(int boardNo);
	
	BoardDTO selectBoardAndReply(int boardNo);
	
	List<BoardDTO> selectSearchList(Map<String, String> map, RowBounds rb);
	
	// 1절
	int updateBoard(BoardDTO board);
	
	int deleteBoard(int boardNo);
	
	int searchedCount(Map<String, String> map);
	
	
	
	int insertReply(ReplyDTO reply);
	
	
}
