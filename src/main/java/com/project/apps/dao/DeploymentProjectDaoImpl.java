package com.project.apps.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.apps.model.DeploymentProject;

public class DeploymentProjectDaoImpl implements DeploymentProjectDao {
	@Autowired
	private DataSource dataSource;


	public void addDeploymentProject(DeploymentProject project) {
			String query = "INSERT INTO deploymentProject (nameProject) VALUES (?)";
			Connection con = null;
			PreparedStatement ps = null;

			try {
				con = dataSource.getConnection();
				ps = con.prepareStatement(query);
				ps.setString(1, project.getNameJob());
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

	public List<DeploymentProject> getlistDeploymentProjects() {
		List<DeploymentProject> listProjects = new ArrayList<DeploymentProject>();
		String query = "select * from deploymentProject";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				DeploymentProject project = new DeploymentProject();
				project.setNameJob(rs.getString("nameProject"));
				project.setDeploymentProjectID(rs.getInt("deploymentProjectID"));
				listProjects.add(project);
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
		return listProjects;
	}

	public DeploymentProject getProject(String name) {
		String query = "SELECT * FROM deploymentProject WHERE UPPER(nameProject) = ?";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DeploymentProject project = new DeploymentProject();

		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, name);

			rs = ps.executeQuery();
			if (rs.next()) {
				project.setNameJob(rs.getString("nameProject"));
				project.setDeploymentProjectID(rs.getInt("deploymentProjectID"));
				
			} else {
				project = null;
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
		return project;
	}

}
