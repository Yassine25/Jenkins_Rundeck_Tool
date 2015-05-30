package com.project.apps.dao;

import java.util.List;

import com.project.apps.model.DeploymentProject;

public interface DeploymentProjectDao {
	public void addDeploymentProject(DeploymentProject project);
	public List<DeploymentProject> getlistDeploymentProjects();
	public DeploymentProject getProject(String name);
}
