package com.revature.service;

import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.UserDao;

public class UserService {

	private UserDao dao;

	public UserService(){
		this.dao = new UserDao();
	}

	public User getUserByUsername(String username) {
		return dao.getUserByUsername(username);
	}

	public User register(UsernamePasswordAuthentication registerRequest) {
		return dao.createUser(registerRequest);
	}
}
