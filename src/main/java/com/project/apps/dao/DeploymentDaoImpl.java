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

import com.project.apps.model.Deployment;

public class DeploymentDaoImpl implements DeploymentDao {
	@Autowired
	private DataSource dataSource;


	public void addDeployment(ArrayList<Deployment> ListDeployments) {
		String query = "INSERT INTO Deployment (deploymentJobID,duration,dateDeployment,executionID) VALUES (?,?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(query);

			for (Deployment deployment : ListDeployments) {
				ps.setString(1, deployment.getDeploymentJobID());
				ps.setLong(2, deployment.getDuration());
				ps.setTimestamp(3, deployment.getDateDeployment());
				ps.setInt(4,deployment.getExecutionID());
				ps.addBatch();
			}

			ps.executeBatch();
			con.commit();
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

	public List<Deployment> getListDeployments() {
		List<Deployment> listDeployments = new ArrayList<Deployment>();
		String query = "select deploymentID,deploymentJobID, duration, dateDeployment, executionID from Deployment";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				Deployment deployment = new Deployment();
				deployment.setDeploymentID(rs.getInt("deploymentID"));
				deployment.setDeploymentJobID(rs.getString("deploymentJobID"));
				deployment.setDuration(rs.getLong("duration"));
				deployment.setDateDeployment(rs.getTimestamp("dateDeployment"));
				deployment.setExecutionID(rs.getInt("executionID"));
				listDeployments.add(deployment);
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
		return listDeployments;
	}

	public Deployment getDeployment(int executionID, String deploymentJobID) {
		String query = "SELECT * FROM Deployment WHERE executionID = ? and deploymentJobID = ?";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Deployment deployment = new Deployment();

		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1, executionID);
			ps.setString(2, deploymentJobID);

			rs = ps.executeQuery();
			if (rs.next()) {
				deployment.setDeploymentJobID(rs.getString("deploymentJobID"));
				deployment.setDateDeployment(rs.getTimestamp("dateDeployment"));
			} else {
				deployment = null;
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
		return deployment;
	}


	public Map<String, Float> getAverageDurationReleases() {
		Map<String, Float> listAverageReleaseDuration = new LinkedHashMap<String, Float>();
		String query = "SELECT ROUND(AVG(deployment.duration/6000),2)AS average, nameCustomer FROM customerType,deploymentJob,deployment  WHERE customerType.customerID = deploymentJob.customerID AND deployment.deploymentJobID = deploymentJob.deploymentJobID AND deploymentJob.nameJob LIKE '%release%' GROUP BY customerType.nameCustomer ORDER BY average ASC";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// for (int i = 0; i < queries.length; i++) {
			con = dataSource.getConnection();
			// ps = con.prepareStatement(queries[i]);
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				listAverageReleaseDuration.put(rs.getString("nameCustomer"),rs.getFloat(1));
				// }
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

	public Map<String, Float> getAverageDurationContinuous() {
		Map<String, Float> listAverageContinuousDuration = new LinkedHashMap<String, Float>();
		String query = "SELECT ROUND(AVG(deployment.duration/6000),2)AS average, nameCustomer FROM customerType,deploymentJob,deployment  WHERE customerType.customerID = deploymentJob.customerID AND deployment.deploymentJobID = deploymentJob.deploymentJobID AND deploymentJob.nameJob LIKE '%continuous%' GROUP BY customerType.nameCustomer ORDER BY average ASC";

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

	public Map<String, Float> getStatisticsContinous(String customer) {
		Map<String, Float> listStatisticsContinuous = new LinkedHashMap<String, Float>();
		//String query = "SELECT  ROUND(AVG(releases.duration/60000),2) AS average, ROUND(MIN(releases.duration/60000),2) AS minimal, ROUND(MAX(releases.duration/60000),2) AS maximal FROM releases, job WHERE UPPER(job.nameJob) LIKE ? and UPPER(job.nameJob) LIKE '%RELEASE%' AND job.jobID = releases.jobID";
		
		String query = "SELECT  ROUND(AVG(deployment.duration/60000),2) AS average, ROUND(MIN(deployment.duration/60000),2) AS minimal, ROUND(MAX(deployment.duration/60000),2) AS maximal FROM deployment, deploymentJob, customerType WHERE UPPER(customerType.nameCustomer) = ? AND UPPER(deploymentJob.nameJob) LIKE '%CONTINUOUS%' AND deploymentJob.customerID = customerType.customerID AND deployment.deploymentJobID = deploymentJob.deploymentJobID";
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1,customer);
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
		//String query = "SELECT  ROUND(AVG(releases.duration/60000),2) AS average, ROUND(MIN(releases.duration/60000),2) AS minimal, ROUND(MAX(releases.duration/60000),2) AS maximal FROM releases, job WHERE UPPER(job.nameJob) LIKE ? and UPPER(job.nameJob) LIKE '%RELEASE%' AND job.jobID = releases.jobID";
		
		String query = "SELECT  ROUND(AVG(deployment.duration/60000),2) AS average, ROUND(MIN(deployment.duration/60000),2) AS minimal, ROUND(MAX(deployment.duration/60000),2) AS maximal FROM deployment,deploymentJob, customerType WHERE UPPER(customerType.nameCustomer) = ? AND UPPER(deploymentJob.nameJob) LIKE '%RELEASE%' AND deploymentJob.customerID = customerType.customerID AND deployment.deploymentJobID = deploymentJob.deploymentJobID";
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1,customer);
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
	
	public List<ReleaseViewModel> getListDeploymentsByMonth(String customerType,int year, int month) {
		List<ReleaseViewModel> listReleaseByMonth = new ArrayList<ReleaseViewModel>();
		String query = "SELECT deployment.duration as duration, deployment.dateDeployment as dateDeployment FROM deployment,deploymentJob,customerType WHERE deployment.deploymentJobID = deploymentJob.deploymentJobID AND customerType.customerID = deploymentJob.customerID AND customerType.nameCustomer = ? AND YEAR(deployment.dateDeployment) = ? AND MONTH(deployment.dateDeployment) = ? ORDER BY deployment.dateDeployment DESC ";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1,customerType);
			ps.setInt(2, year);
			ps.setInt(3, month);
			rs = ps.executeQuery();
			while (rs.next()) {
				String dateTime = rs.getTimestamp("dateDeployment").toString();
				String[] splitDateTime = dateTime.split(" ");

				ReleaseViewModel release = new ReleaseViewModel();
				release.setDate(splitDateTime[0]);
				release.setTime(splitDateTime[1]);
				release.setDuration(rs.getLong("duration"));
				listReleaseByMonth.add(release);
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
		return listReleaseByMonth;
	}

	public int getCountDeployments(String customer, int year, int month) {
		String query = "SELECT COUNT(*) FROM deployment,deploymentJob,customerType WHERE deployment.deploymentJobID = deploymentJob.deploymentJobID AND customerType.customerID = deploymentJob.customerID AND customerType.nameCustomer = ? AND YEAR(deployment.dateDeployment) = ? AND MONTH(deployment.dateDeployment) = ? ORDER BY deployment.dateDeployment DESC ";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int countDeployments = 0;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1,customer);
			ps.setInt(2, year);
			ps.setInt(3, month);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				countDeployments = rs.getInt(1);
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
		return countDeployments;
	}
}
