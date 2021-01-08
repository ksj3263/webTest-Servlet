package com.webTest.testmvc.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.webTest.testmvc.service.UserService;
import com.webTest.testmvc.vo.Pagination;
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

		UserService userService = null;
		User user = null;
		HttpSession session = null;
		
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
			case "/logout.do":
				session = request.getSession();
				session.invalidate();
				view = "user/login";
				break;
			case "/user-list.do":
				String reqPage = request.getParameter("page");
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
				
				view = "user/edit-result";
				break;
			case "/user-delete.do":
				idx = request.getParameter("u_idx");
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
		}
		
		if (view != null) {
			RequestDispatcher rd = request.getRequestDispatcher(view + ".jsp");
			rd.forward(request, response);
		}
	}
	
	String checkSession(HttpServletRequest request, HttpServletResponse response, String command) {
		HttpSession session = request.getSession();
		
		String[] authlist = {
				"/user-list.do",
				"/user-insert.do",
				"/user-insert-process.do",
				"/user-detail.do",
				"/user-edit.do",
				"/user-edit-process.do",
				"/logout.do"
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
}

