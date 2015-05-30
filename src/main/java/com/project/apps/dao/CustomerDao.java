package com.project.apps.dao;

import java.util.List;

import com.project.apps.model.Customer;



public interface CustomerDao {
	public void addCustomer(String nameCustomer);
	public Customer getCustomer(String nameCustomer);
	public List<String> getListCustomerType();
}
