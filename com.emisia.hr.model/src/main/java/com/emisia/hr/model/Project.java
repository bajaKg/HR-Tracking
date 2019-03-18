package com.emisia.hr.model;

import java.util.Date;

public class Project {
	
	private int id;
	private String projectName;
	private String clientName;
	private Date projectStartDate;
	private Date projectEndDate;
	private String projectDescription;
	
	public Project(int id, String projectName, String clientName, Date projectStartDate, Date projectEndDate, String projectDescription) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.clientName = clientName;
		this.projectStartDate = projectStartDate;
		this.projectEndDate = projectEndDate;
		this.projectDescription = projectDescription;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Date getProjectStartDate() {
		return projectStartDate;
	}
	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}
	public Date getProjectEndDate() {
		return projectEndDate;
	}
	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
}
