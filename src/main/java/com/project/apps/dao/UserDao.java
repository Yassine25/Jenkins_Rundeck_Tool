package com.project.apps.dao;

import java.util.List;

import com.project.apps.model.User;

public interface UserDao {
	public void addUser(User user);
	public List<User> getUsers();

}
