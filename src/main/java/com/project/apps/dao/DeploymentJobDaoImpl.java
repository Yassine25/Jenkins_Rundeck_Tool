package com.project.apps.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.apps.model.deploymentJob;

public class DeploymentJobDaoImpl implements DeploymentJobDao {
	@Autowired
	private DataSource dataSource;

	public void addDeploymentJob(ArrayList<deploymentJob> list) {
		String query = "INSERT INTO deploymentJob (deploymentJobID,nameJob,deploymentProjectID) VALUES (?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(query);

			for (deploymentJob job : list) {
				ps.setString(1, job.getDeploymentJobID());
				ps.setString(2, job.getNameJob());
				ps.setInt(3, job.getDeploymentProjectID());
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

	public List<deploymentJob> getListDeploymentJobs() {
		List<deploymentJob> listJobs = new ArrayList<deploymentJob>();
		String query = "select deploymentJobID,nameJob, deploymentProjectID from deploymentJob";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				deploymentJob job = new deploymentJob();
				job.setNameJob(rs.getString("nameJob"));
				job.setDeploymentJobID(rs.getString("deploymentJobID"));
				job.setDeploymentProjectID(rs.getInt("deploymentProjectID"));
				listJobs.add(job);
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
		return listJobs;
	}

	public deploymentJob getDeploymentJob(String jobID) {
		String query = "SELECT * FROM deploymentJob WHERE deploymentJobID = ?";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		deploymentJob job = new deploymentJob();

		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, jobID);

			rs = ps.executeQuery();
			if (rs.next()) {
				job.setNameJob(rs.getString("nameJob"));
				job.setDeploymentJobID(rs.getString("deploymentJobID"));
				job.setDeploymentProjectID(rs.getInt("deploymentProjectID"));
			} else {
				job = null;
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
		return job;
	}

	public List<String> getNameDeploymentJobs() {
		List<String> listJobs = new ArrayList<String>();
		String query = "select UPPER(nameJob) as nameJob from deploymentJob WHERE customerID IS NULL order by nameJob ASC";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				deploymentJob job = new deploymentJob();
				String name = rs.getString("nameJob");

				listJobs.add(name);
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
		return listJobs;
	}

	public void addCustomerID(String nameJob, int id) {
		String query = "UPDATE deploymentJob SET customerID = ?  where nameJob = ?";
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ps.setString(2, nameJob);
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

	public void updateNameJob(String jobID, String nameJob) {
			String query = "UPDATE deploymentJob SET nameJob = ?  where deploymentJobID = ?";
			Connection con = null;
			PreparedStatement ps = null;

			try {
				con = dataSource.getConnection();
				ps = con.prepareStatement(query);
				ps.setString(1,nameJob );
				ps.setString(2, jobID);
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
}
