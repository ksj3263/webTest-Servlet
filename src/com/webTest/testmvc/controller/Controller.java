package com.webTest.testmvc.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.webTest.testmvc.service.BoardService;
import com.webTest.testmvc.service.ReplyService;
import com.webTest.testmvc.service.UserService;
import com.webTest.testmvc.vo.Board;
import com.webTest.testmvc.vo.Pagination;
import com.webTest.testmvc.vo.Pagination_B;
import com.webTest.testmvc.vo.Reply;
import com.webTest.testmvc.vo.User;

@WebServlet("*.do")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");

		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		String view = null;
		String idx = null;
		String pw = null;
		String id = null;
		String reqPage;
		Date today = new Date();
		SimpleDateFormat format;

		UserService userService = null;
		User user = null;
		HttpSession session = null;
		
		Board board = null;
		BoardService boardService = null;
		ArrayList<Board> b_list = null;
		Pagination_B pgb = null;
		
		Reply reply = null;
		ReplyService replyService = null;
		ArrayList<Reply> r_list = null;
		
		int page = 1;
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		command = checkSession(request, response, command);
		
		switch (command) {
			case "/user-login.do":
				view = "user/login";
				break;
			case "/access-denied.do":
				view = "user/access-denied";
				break;
			case "/user-login-process.do":
				idx = request.getParameter("login_id");
				pw = request.getParameter("login_password");
				
				userService = UserService.getInstance();
				user = userService.loginUser(idx, pw);
				
				if(user!=null) {
					session = request.getSession();
					session.setAttribute("user", user);
					view = "user/login-result";
				} else {
					view = "user/login-fail";
				}
				break;
			case "/user-main.do":
				view = "user/login-result";
				break;
			case "/logout.do":
				session = request.getSession();
				session.invalidate();
				view = "user/login";
				break;
			case "/user-list.do":
				reqPage = request.getParameter("page");
				if(reqPage!=null)
					page=Integer.parseInt(reqPage);
				
				userService = UserService.getInstance();
				ArrayList<User> list = userService.getUsers(page);
				Pagination pagination = new Pagination(page);
				
				request.setAttribute("list", list);
				request.setAttribute("pagination", pagination);
				
				view = "user/list";
				break;				
			case "/user-insert.do":
				view = "user/insert";
				break;				
			case "/user-insert-process.do":
				user = new User();
				user.setU_id(request.getParameter("id"));
				user.setU_pw(request.getParameter("password"));
				user.setU_name(request.getParameter("name"));
				user.setU_tel(request.getParameter("tel1") + "-" + request.getParameter("tel2") + "-" + request.getParameter("tel3"));
				user.setU_age(request.getParameter("age"));
				user.setU_tel1(request.getParameter("tel1"));
				user.setU_tel2(request.getParameter("tel2"));
				user.setU_tel3(request.getParameter("tel3"));
				
				userService = UserService.getInstance();
				userService.insertUser(user);
				
				view = "user/insert-result";
				break;
			case "/user-detail.do":
				idx = request.getParameter("u_idx");
				userService = UserService.getInstance();
				user = userService.findUser(idx);
				
				request.setAttribute("user", user);
				view = "user/detail";
				break;
			case "/user-edit.do":
				idx = request.getParameter("u_idx");
				
				if(!checkAuthority(request, Integer.parseInt(idx))) {
					view = "user/access-denied-authority";
					break;
				}
				
				userService = UserService.getInstance();
				user = userService.findUser(idx);
								
				request.setAttribute("user", user);
				view = "user/edit";
				break;
			case "/user-edit-process.do":
				user = new User();
				user.setU_idx(Integer.parseInt(request.getParameter("u_idx")));
				user.setU_id(request.getParameter("edit_id"));
				user.setU_pw(request.getParameter("edit_password"));
				user.setU_name(request.getParameter("edit_name"));
				user.setU_tel(request.getParameter("edit_tel1") + "-" + request.getParameter("edit_tel2") + "-" + request.getParameter("edit_tel3"));
				user.setU_age(request.getParameter("edit_age"));
				user.setU_tel1(request.getParameter("edit_tel1"));
				user.setU_tel2(request.getParameter("edit_tel2"));
				user.setU_tel3(request.getParameter("edit_tel3"));
				
				userService = UserService.getInstance();
				userService.editUser(user);
				
				view = "user/edit-result";	// test
				break;
			case "/user-delete.do":
				idx = request.getParameter("u_idx");
				
				if(!checkAuthority(request, Integer.parseInt(idx))) {
					view = "user/access-denied-authority";
					break;
				}
				
				userService = UserService.getInstance();
				userService.deleteUser(idx);
				
				view = "user/delete-result";
				break;
			case "/user-dup.do":
				id = request.getParameter("u_id");
				userService = UserService.getInstance();
				if(userService.dupUser(id)) {
					response.getWriter().print("true");
				} else {
					response.getWriter().print("false");
				}
				
				view = null;
				break;
			case "/board.do":
				reqPage = request.getParameter("page");
				if(reqPage!=null)
					page=Integer.parseInt(reqPage);
				
				boardService = BoardService.getInstance();
				b_list = boardService.getBoards(page);
				pgb = new Pagination_B(page);
				
				request.setAttribute("list", b_list);
				request.setAttribute("pagination", pgb);	
							
				view = "user/board";
				break;
				
			case "/board-write.do":
				view = "user/board-write";
				break;
				
			case "/board-write-result.do":
				board = new Board();
				board.setB_title(request.getParameter("title"));
				board.setB_content(request.getParameter("content"));
				
				session = request.getSession();
				user = (User)session.getAttribute("user");
				board.setB_writer(user.getU_id());
				board.setU_idx(user.getU_idx());
								
				format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
				board.setB_date(format.format(today));
				
				boardService = BoardService.getInstance();
				boardService.writeBoard(board);
								
				view = "user/board-result";
				break;
				
			case "/board-detail.do":
				idx = request.getParameter("b_idx");
				boardService = BoardService.getInstance();
				board = boardService.findBoard(idx);				
				request.setAttribute("board", board);
				
				replyService = ReplyService.getInstance();
				r_list = replyService.getReply(board.getB_idx());
				request.setAttribute("list", r_list);
				view = "user/board-detail";
				break;
				
			case "/board-edit.do":
				idx = request.getParameter("b_idx");				
				
				boardService = BoardService.getInstance();
				board = boardService.findBoard(idx);				

				if(!checkAuthority(request, board.getU_idx())) {
					view = "user/access-denied-authority";
					break;
				}
								
				request.setAttribute("board", board);				
				view = "user/board-edit";
				break;
				
			case "/board-edit-process.do":
				board = new Board();
				board.setB_idx(Integer.parseInt(request.getParameter("edit_idx")));
				board.setB_title(request.getParameter("edit_title"));
				board.setB_content(request.getParameter("edit_content"));
				format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
				board.setB_date(format.format(today));
				
				boardService = BoardService.getInstance();
				boardService.editBoard(board);
				
				view = "user/board-edit-result";
				break;
				
			case "/board-reply.do":
				reply = new Reply();
				reply.setR_content(request.getParameter("content"));
				format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
				reply.setR_date(format.format(today));
				reply.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
								
				session = request.getSession();
				user = (User)session.getAttribute("user");
				reply.setR_writer(user.getU_id());
				reply.setU_idx(user.getU_idx());
				
				replyService = ReplyService.getInstance();
				replyService.writeReply(reply);		
				
				r_list = replyService.getReply(reply.getB_idx());
				request.setAttribute("list", r_list);
				view = "user/reply-list";
				break;
				
			case "/board-reply-delete.do":
				idx = request.getParameter("r_idx");
				replyService = ReplyService.getInstance();
				reply = replyService.findReply(idx);
								
				if(!checkAuthority(request, reply.getU_idx())) {
					view = "user/false";
					break;
				}
				
				replyService.deleteReply(idx);
				
				r_list = replyService.getReply(reply.getB_idx());
				request.setAttribute("list", r_list);
				view = "user/reply-list";
				break;
			
			case "/board-reply-edit.do":
				idx = request.getParameter("r_idx");
				replyService = ReplyService.getInstance();
				reply = replyService.findReply(idx);
								
				if(!checkAuthority(request, reply.getU_idx())) {
					view = "user/false";
					break;
				}
				
				request.setAttribute("reply", reply);
				view = "user/reply-edit";
				break;
				
			case "/board-reply-edit-process.do":
				reply = new Reply();
				reply.setR_content(request.getParameter("content"));
				reply.setR_idx(Integer.parseInt(request.getParameter("r_idx")));
				format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
				reply.setR_date(format.format(today));
				
				replyService = ReplyService.getInstance();
				replyService.editReply(reply);				
				
				r_list = replyService.getReply(Integer.parseInt(request.getParameter("b_idx")));
				request.setAttribute("list", r_list);
				view = "user/reply-list";
				break;
				
			case "/board-add.do":
				boardService = BoardService.getInstance();
				board = boardService.findBoard(request.getParameter("b_idx"));
				request.setAttribute("board", board);
				view = "user/board-add";
				break;
				
			case "/board-add-result.do":
				boardService = BoardService.getInstance();
				board = new Board();
				board.setB_title(request.getParameter("title"));
				board.setB_content(request.getParameter("content"));
				
				session = request.getSession();
				user = (User)session.getAttribute("user");
				board.setB_writer(user.getU_id());
				board.setU_idx(user.getU_idx());
				
				Board board2 = new Board();
				board2 = boardService.findBoard(request.getParameter("idx"));
				board.setB_base(board2.getB_base());
				board.setB_layer(board2.getB_layer()+1);
				board.setB_order(board2.getB_order());
				
				format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
				board.setB_date(format.format(today));
				
				boardService.addBoard(board);
				
				view = "user/board-result";
				break;
				
			case "/board-search.do":
				String key = request.getParameter("key");
				boardService = BoardService.getInstance();
				
				reqPage = request.getParameter("page");
				if(reqPage!=null)
					page=Integer.parseInt(reqPage);
												
				b_list = boardService.searchBoard(key, page);
				int searchBoardCount = boardService.getSearchBoardCount(key);
				
				pgb = new Pagination_B(page, searchBoardCount);
				
				request.setAttribute("list", b_list);
				request.setAttribute("pagination", pgb);
				request.setAttribute("key", key);
				
				view = "user/board-search";
				break;
				
			case "/board-delete.do":
				idx = request.getParameter("b_idx");
				boardService = BoardService.getInstance();
				board = boardService.findBoard(idx);
								
				if(!checkAuthority(request, board.getU_idx())) {
					view = "user/access-denied-authority";
					break;
				}
				
				boardService.deleteBoard(idx);
				
				view = "user/board-delete-result";
				break;
		}
		
		if (view != null) {
			RequestDispatcher rd = request.getRequestDispatcher(view + ".jsp");
			rd.forward(request, response);
		}
	}
	
	String checkSession(HttpServletRequest request, HttpServletResponse response, String command) {
		HttpSession session = request.getSession();
		
		String[] authlist = {				
				"/user-insert.do",
				"/user-insert-process.do",
				"/user-edit.do",
				"/user-edit-process.do",
				"/user-delete.do",
				"/logout.do",
				"/board-write.do",
				"/board-write-result.do",
				"/board-add.do",
				"/board-add-result.do",
				"/board-edit.do",
				"/board-edit-process.do",
				"/board-delete.do",
				"/board-reply-edit.do",
				"/board-reply-edit-process.do",
				"/board-reply-delete.do"
		};
		
		for(String item : authlist) {
			if(item.equals(command)) {
				if(session.getAttribute("user") == null) {
					command = "/access-denied.do";
				}
			}
		}
		
		return command;
	}
	
	boolean checkAuthority(HttpServletRequest request, int idx) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		if(user.getU_idx() != idx) {
			return false;
		}
		return true;
	}
}

