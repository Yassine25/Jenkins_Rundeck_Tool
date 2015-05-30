package com.project.apps.model;

public class deploymentJob {
	private String deploymentJobID;
	private String nameJob;
	private int deploymentProjectID;
	
	
	public String getDeploymentJobID() {
		return deploymentJobID;
	}
	public void setDeploymentJobID(String deploymentID) {
		this.deploymentJobID = deploymentID;
	}
	public String getNameJob() {
		return nameJob;
	}
	public void setNameJob(String nameJob) {
		this.nameJob = nameJob;
	}
	public int getDeploymentProjectID() {
		return deploymentProjectID;
	}
	public void setDeploymentProjectID(int deploymentProjectID) {
		this.deploymentProjectID = deploymentProjectID;
	}

}
