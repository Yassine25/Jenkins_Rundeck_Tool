package com.project.apps.dao;


import java.util.List;

import com.project.apps.model.Job;

public interface JobDao {
	public void addJob(String nameJob);
	public Job getJob(String name);
	public List<Job> getListJobs();
	public List<String> getNameJobs();
	public void addCustomerID(String nameJob, int id);

}
