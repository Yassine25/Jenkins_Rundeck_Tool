package com.project.apps.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import viewModel.ReleaseViewModel;

import com.project.apps.model.Release;

public class ReleaseDaoImpl implements ReleasesDao {

	@Autowired
	private DataSource dataSource;


	public void addReleases(Release c) {
		String query = "insert into releases (duration,dateRelease,jobID) values (?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setLong(1, c.getDuration());
			ps.setTimestamp(2, c.getDate());
			ps.setInt(3, c.getJobID());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Release getRelease(Release r) {
		String query = "SELECT * FROM releases WHERE jobID = ? and dateRelease = ?";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Release release = new Release();

		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1, r.getJobID());
			ps.setTimestamp(2, r.getDate());

			rs = ps.executeQuery();
			if (rs.next()) {
				r.setJobID(rs.getInt("jobID"));
				r.setDate(rs.getTimestamp("dateRelease"));
			} else {
				release = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return release;

	}

	public List<Release> listReleases() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Float> getAverageDurationContinuous() {
		Map<String, Float> listAverageContinuousDuration = new LinkedHashMap<String, Float>();
		String query = "SELECT ROUND(AVG(releases.duration/60000),2)AS average, nameCustomer FROM customerType,job,releases  WHERE customerType.customerID = job.customerID AND releases.jobID = job.JobID AND job.nameJob LIKE '%continuous%' GROUP BY customerType.nameCustomer ORDER BY average ASC";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				listAverageContinuousDuration.put(rs.getString("nameCustomer"),rs.getFloat(1));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// return Operations.sortHashMap(listAverageContinuousDuration);

		return listAverageContinuousDuration;
	}

	public Map<String, Float> getAverageDurationReleases() {
		Map<String, Float> listAverageReleaseDuration = new LinkedHashMap<String, Float>();
		String query = "SELECT ROUND(AVG(releases.duration/60000),2)AS average, nameCustomer FROM customerType,job,releases  WHERE customerType.customerID = job.customerID AND releases.jobID = job.jobID AND job.nameJob LIKE '%release%' GROUP BY customerType.nameCustomer ORDER BY average ASC";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				listAverageReleaseDuration.put(rs.getString("nameCustomer"),rs.getFloat(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// return Operations.sortHashMap(listAverageContinuousDuration);

		return listAverageReleaseDuration;
	}

	public Map<String, Float> getStatisticsContinous(String customer) {
		Map<String, Float> listStatisticsContinuous = new LinkedHashMap<String, Float>();
		String query = "SELECT  ROUND(AVG(releases.duration/60000),2) AS average, ROUND(MIN(releases.duration/60000),2) AS minimal, ROUND(MAX(releases.duration/60000),2) AS maximal FROM releases, job, customerType WHERE UPPER(customerType.nameCustomer) = ? AND UPPER(job.nameJob) NOT LIKE '%RELEASE%' AND job.customerID = customerType.customerID AND releases.jobID = job.jobID";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, customer);
			rs = ps.executeQuery();
			while (rs.next()) {
				listStatisticsContinuous.put("minimun", rs.getFloat("minimal"));
				listStatisticsContinuous.put("average", rs.getFloat("average"));
				listStatisticsContinuous.put("maximum", rs.getFloat("maximal"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listStatisticsContinuous;
	}

	public Map<String, Float> getStatisticsReleases(String customer) {
		Map<String, Float> listStatisticsReleases = new LinkedHashMap<String, Float>();
		String query = "SELECT  ROUND(AVG(releases.duration/60000),2) AS average, ROUND(MIN(releases.duration/60000),2) AS minimal, ROUND(MAX(releases.duration/60000),2) AS maximal FROM releases, job, customerType WHERE UPPER(customerType.nameCustomer) = ? AND UPPER(job.nameJob) LIKE '%CONTINUOUS%' AND job.customerID = customerType.customerID AND releases.jobID = job.jobID";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, "%" + customer + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				listStatisticsReleases.put("minimun", rs.getFloat("minimal"));
				listStatisticsReleases.put("average", rs.getFloat("average"));
				listStatisticsReleases.put("maximum", rs.getFloat("maximal"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listStatisticsReleases;
	}

	public List<ReleaseViewModel> getListReleasesByMonth(String customerType,int year, int month) {
		List<ReleaseViewModel> listContinious = new ArrayList<ReleaseViewModel>();
		String query = "SELECT releases.duration as duration, releases.dateRelease FROM releases,job,customerType WHERE releases.jobID = job.jobID AND customerType.customerID = job.customerID AND customerType.nameCustomer = ? AND YEAR(releases.dateRelease) = ? AND MONTH(releases.dateRelease) = ? ORDER BY releases.dateRelease DESC ";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, customerType);
			ps.setInt(2, year);
			ps.setInt(3, month);
			rs = ps.executeQuery();
			while (rs.next()) {
				String dateTime = rs.getTimestamp("dateRelease").toString();
				String[] splitDateTime = dateTime.split(" ");

				ReleaseViewModel release = new ReleaseViewModel();
				release.setDate(splitDateTime[0]);
				release.setTime(splitDateTime[1]);
				release.setDuration(rs.getLong("duration"));
				listContinious.add(release);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listContinious;
	}

	public int getLowestYearRelease() {
		String query = "SELECT MIN(YEAR(dateRelease)) as year FROM releases";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int lowestYear = 0;

		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);

			rs = ps.executeQuery();
			if (rs.next()) {
				lowestYear = rs.getInt("year");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lowestYear;

	}

	public int getCountReleases(String customer, int year, int month) {
		String query = "SELECT COUNT(*) FROM releases,job,customerType WHERE releases.jobID = job.jobID AND customerType.customerID = job.customerID AND customerType.nameCustomer = ? AND YEAR(releases.dateRelease) = ? AND MONTH(releases.dateRelease) = ? ORDER BY releases.dateRelease DESC ";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int countReleases = 0;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, customer);
			ps.setInt(2, year);
			ps.setInt(3, month);
			rs = ps.executeQuery();
			if (rs.next()) {
				countReleases = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return countReleases;
	}

}
