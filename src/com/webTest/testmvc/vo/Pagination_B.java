package com.webTest.testmvc.vo;

import com.webTest.testmvc.service.BoardService;

public class Pagination_B {
	int page;
	int pageNum;
	int startPage;
	int endPage;
	int lastPage;
	int prevPage;
	int nextPage;
	int boardCount;
	public static final int pageUnit=5;
	public static final int perPage=7;
	BoardService boardService = null;
	
	public Pagination_B() {
		
	}
	
	public Pagination_B(int page) {
		this.page = page;
		startPage = ((page-1)/pageUnit) * pageUnit + 1;
		boardService = BoardService.getInstance();
		boardCount = boardService.getBoardCount();		
		lastPage = (int)Math.ceil(boardCount / (float)perPage);
		endPage = startPage + pageUnit-1;
		endPage = endPage < lastPage ? endPage : lastPage;
		prevPage=startPage-1;
		nextPage = (startPage+pageUnit);
	}

	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getLastPage() {
		return lastPage;
	}
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
	public int getPageUnit() {
		return pageUnit;
	}
	public int getPerPage() {
		return perPage;
	}
	public int getPrevPage() {
		return prevPage;
	}
	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}	
}
