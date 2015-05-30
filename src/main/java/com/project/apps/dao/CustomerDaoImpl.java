package com.project.apps.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.apps.model.Customer;

public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	private DataSource dataSource;

	public void addCustomer(String customerName) {
		String query = "INSERT INTO customerType (nameCustomer) VALUES (?)";
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, customerName);
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

	public Customer getCustomer(String name) {
		String query = "SELECT * FROM customerType WHERE nameCustomer = ?";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Customer customer = new Customer();

		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, name);

			rs = ps.executeQuery();
			if (rs.next()) {
				customer.setId(rs.getInt("customerID"));
				customer.setNameCustomer(rs.getString("nameCustomer"));
			} else {
				customer = null;
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
		return customer;
	}

	public List<String> getListCustomerType() {
		List<String> listCustomerType = new ArrayList<String>();

		String query = "SELECT UPPER(nameCustomer) as nameCustomer FROM customerType";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				listCustomerType.add(rs.getString("nameCustomer"));
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
		return listCustomerType;
	}

}
