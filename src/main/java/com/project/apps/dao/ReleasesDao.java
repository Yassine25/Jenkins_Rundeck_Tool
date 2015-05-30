package com.project.apps.dao;


import java.util.List;
import java.util.Map;
import viewModel.ReleaseViewModel;
import com.project.apps.model.Release;

public interface ReleasesDao {
	public void addReleases(Release r);
	public List<Release>listReleases();
	public Release getRelease(Release release);
	public Map<String,Float> getAverageDurationContinuous();
	public Map<String,Float> getAverageDurationReleases();
	public Map<String,Float> getStatisticsContinous(String customer);
	public Map<String,Float> getStatisticsReleases(String customer);
	public List<ReleaseViewModel> getListReleasesByMonth(String customer,int year,int month);
	public int getLowestYearRelease();
	public int getCountReleases(String customer, int year, int month);
	}
