package com.project.apps.model;

import java.sql.Timestamp;

public class Deployment {
	private int deploymentID;
	private String deploymentJobID;
	private int executionID;
	private long duration;
	private Timestamp dateDeployment;

	public int getDeploymentID() {
		return deploymentID;
	}

	public void setDeploymentID(int deploymentID) {
		this.deploymentID = deploymentID;
	}

	public String getDeploymentJobID() {
		return deploymentJobID;
	}

	public void setDeploymentJobID(String deploymentJobID) {
		this.deploymentJobID = deploymentJobID;
	}
	
	public int getExecutionID() {
		return executionID;
	}
	
	public void setExecutionID(int executionID) {
		this.executionID = executionID;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public Timestamp getDateDeployment() {
		return dateDeployment;
	}

	public void setDateDeployment(Timestamp dateDeployment) {
		this.dateDeployment = dateDeployment;
	}

}
