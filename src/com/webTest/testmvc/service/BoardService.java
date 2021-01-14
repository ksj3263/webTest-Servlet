package com.webTest.testmvc.service;

import java.util.ArrayList;

import com.webTest.testmvc.dao.BoardDAO;
import com.webTest.testmvc.vo.Board;

public class BoardService {
	private static BoardService service = null;
	private static BoardDAO dao = null;
	
	private BoardService() {
		
	}
	
	public static BoardService getInstance() {
		if(service == null) {
			service = new BoardService();
			dao = BoardDAO.getInstance();
		}
		return service;
	}
	
	public void writeBoard(Board board) {
		dao.writeBoard(board);
		dao.writeBase();
	}
	
	public ArrayList<Board> getBoards(int page) {
		return dao.getBoards(page);
	}
	
	public Board findBoard(String idx) {
		return dao.findBoard(idx);
	}
	
	public void editBoard(Board board) {
		dao.editBoard(board);
	}
	
	public int getBoardCount() {
		return dao.getBoardCount();
	}
	
	public int getSearchBoardCount(String key) {
		return dao.getSearchBoardCount(key);
	}
	
	public void addBoard(Board board) {
		dao.addBoard(board);
	}
	
	public ArrayList<Board> searchBoard(String key, int page) {
		return dao.searchBoard(key, page);
	}
	
	public void deleteBoard(String idx) {
		dao.deleteBoard(idx);
	}
}
