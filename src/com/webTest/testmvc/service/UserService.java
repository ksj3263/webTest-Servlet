package com.webTest.testmvc.service;

import java.util.ArrayList;

import com.webTest.testmvc.dao.UserDAO;
import com.webTest.testmvc.vo.User;

public class UserService {
	private static UserService service = null;
	private static UserDAO dao = null;
	
	private UserService() {
		
	}
	
	public static UserService getInstance() {
		if(service == null) {
			service = new UserService();
			dao = UserDAO.getInstance();
		}
		return service;
	}
	
	public ArrayList<User> getUsers(int page) {
		return dao.getUsers(page);
	}
	
	public void insertUser(User user) {
		dao.insertUser(user);
	}
	
	public int getUsersCount() {
		return dao.getUsersCount();
	}
	
	public User loginUser(String idx, String pw) {
		return dao.loginUser(idx, pw);
	}
	
	public User findUser(String idx) {
		return dao.findUser(idx);
	}
	
	public void editUser(User user) {
		 dao.editUser(user);
	}
	
	public void deleteUser(String idx) {
		dao.deleteUser(idx);
	}
	
	public boolean dupUser(String id) {
		return dao.dupUser(id);
	}
}
