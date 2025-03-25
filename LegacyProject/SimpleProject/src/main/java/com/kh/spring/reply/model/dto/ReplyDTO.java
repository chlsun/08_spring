package com.kh.spring.reply.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyDTO {
	
	private int replyNo;
	private String replyWriter;
	private String replyContent;
	private Date createDate;
	private int refBoardNo;
	
}
