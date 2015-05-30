package com.project.apps.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import viewModel.ReleaseViewModel;

import com.project.apps.model.Deployment;

public interface DeploymentDao {
	public void addDeployment(ArrayList<Deployment> deloyments);
	public List<Deployment> getListDeployments();
	public Deployment getDeployment(int executionID, String deploymentJobID);
	public Map<String,Float> getAverageDurationContinuous();
	public Map<String,Float> getAverageDurationReleases();
	public Map<String,Float> getStatisticsContinous(String customer);
	public Map<String,Float> getStatisticsReleases(String customer);
	public List<ReleaseViewModel> getListDeploymentsByMonth(String customer,int year,int month);
	public int getCountDeployments(String customer, int year, int month);
}
