package com.project.apps.model;

public class Customer {
	private int id;
	private String nameCustomer;

	public Customer() {
	}

	public Customer(int id, String nameCustomer) {
		this.id = id;
		this.nameCustomer = nameCustomer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameCustomer() {
		return nameCustomer;
	}

	public void setNameCustomer(String nameCustomer) {
		this.nameCustomer = nameCustomer;
	}
}
