package com.project.apps.model;

import javax.validation.constraints.Pattern;

public class ChangePassword {
	private String oldPassword;
	@Pattern(regexp="((?=.*\\d)(?=.*[A-Z]){6,20}")
	private String newPassword;
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
