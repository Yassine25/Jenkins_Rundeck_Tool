package com.project.apps.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.apps.model.Job;

public class JobDaoImpl implements JobDao {

	@Autowired
	private DataSource dataSource;

	public void addJob(String nameJob) {
		String query = "INSERT INTO job (nameJob) VALUES (?)";
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, nameJob);
			//ps.setInt(2, customerId);
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

	public Job getJob(String name) {
		String query = "SELECT * FROM job WHERE nameJob = ?";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Job job = new Job();

		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, name);

			rs = ps.executeQuery();
			if (rs.next()) {
				job.setId(rs.getInt("jobID"));
				job.setNameJob(rs.getString("nameJob"));
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

	public List<Job> getListJobs() {
		List<Job> listJobs = new ArrayList<Job>();
		String query = "select jobID,nameJob from job";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				Job job = new Job();
				job.setNameJob(rs.getString("nameJob"));
				job.setId(rs.getInt("jobID"));
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
	
	public List<String> getNameJobs() {
		List<String> listJobs = new ArrayList<String>();
		String query = "select UPPER(nameJob) as nameJob from job WHERE customerID IS NULL";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
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
		String query = "UPDATE job SET customerID = ? where nameJob = ?";
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
}
