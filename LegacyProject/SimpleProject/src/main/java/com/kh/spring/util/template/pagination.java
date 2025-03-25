package com.kh.spring.util.template;

import com.kh.spring.util.model.dto.PageInfo;

public class pagination {

	public static PageInfo getPageInfo(int count, int currentPage, int boardLimit, int pageLimit) {
		
		int maxPage = (int)Math.ceil((double)count / boardLimit);
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		int endPage = startPage + pageLimit - 1;
		
		if(endPage > maxPage) { endPage = maxPage; }
		
		// new PageInfo(count, currentPage, boardLimit, pageLimit, maxPage, startPage, endPage);
		
		
		
		// lombok에 builder애노테이션 사용 하면 장점
		// 순서상관없이 넣을 수 있음
		// 원하는 필드만 정해서 넣을 수 있음
		return PageInfo.builder()
					.boardLimit(boardLimit)
					.count(count)
					.currentPage(currentPage)
					.startPage(startPage)
					.endPage(endPage)
					.maxPage(maxPage)
					.pageLimit(pageLimit)
					.build();
	}
}
