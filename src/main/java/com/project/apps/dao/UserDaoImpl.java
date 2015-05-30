package com.project.apps.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.apps.model.User;
import com.project.apps.model.deploymentJob;

public class UserDaoImpl implements UserDao{
	@Autowired
	private DataSource dataSource;
	
	public void addUser(User user) {
		String query = "INSERT INTO users (username,password) VALUES (?,?)";
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getPassWord());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<User> getUsers() {
		List<User> listUsers = new ArrayList<User>();
		String query = "select * from users";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserName(rs.getString("username"));
				user.setEnabled(rs.getBoolean("enabled"));
				listUsers.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listUsers;
	}

}
