package com.webTest.testmvc.service;

import java.util.ArrayList;

import com.webTest.testmvc.dao.ReplyDAO;
import com.webTest.testmvc.vo.Reply;

public class ReplyService {
	private static ReplyService service = null;
	private static ReplyDAO dao = null;
	
	private ReplyService() {
		
	}
	
	public static ReplyService getInstance() {
		if(service == null) {
			service = new ReplyService();
			dao = ReplyDAO.getInstance();
		}
		return service;
	}
	
	public void writeReply(Reply reply) {
		dao.writeReply(reply);
	}
	
	public ArrayList<Reply> getReply(int idx) {
		return dao.getReply(idx);
	}
	
	public Reply findReply(String idx) {
		return dao.findReply(idx);
	}
	
	public void deleteReply(String idx) {
		dao.deleteReply(idx);
	}
	
	public void editReply(Reply reply) {
		dao.editReply(reply);
	}
}
