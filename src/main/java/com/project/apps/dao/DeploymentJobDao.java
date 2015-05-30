package com.project.apps.dao;

import java.util.ArrayList;
import java.util.List;

import com.project.apps.model.deploymentJob;

public interface DeploymentJobDao {
	public void addDeploymentJob(ArrayList<deploymentJob> list);
	public List<deploymentJob> getListDeploymentJobs();
	public deploymentJob getDeploymentJob(String jobID);
	public List<String> getNameDeploymentJobs();
	public void addCustomerID(String nameJob,int id);
	public void updateNameJob(String jobID, String jobName);
}
