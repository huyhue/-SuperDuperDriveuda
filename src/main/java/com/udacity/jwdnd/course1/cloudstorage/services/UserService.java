package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private UsersMapper um;
	public UserService(UsersMapper um) {
		this.um = um;
	}

	public int inUser(User u) {
		return um.createUser(u);
	}

	public User getUser(String name) {
		return um.getUser(name);
	}

	public boolean isOK(String userName) {
		return um.getUser(userName) == null;
	}
}
