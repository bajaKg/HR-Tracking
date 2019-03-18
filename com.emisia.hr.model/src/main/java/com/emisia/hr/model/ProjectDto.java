package com.emisia.hr.model;

import java.util.Date;

public class ProjectDto {
	
	private int id;
	private String projectName;
	private String clientName;
	private Date projectStartDate;
	private Date projectEndDate;
	
	public ProjectDto(int id, String projectName, String clientName,
			Date projectStartDate, Date projectEndDate) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.clientName = clientName;
		this.projectStartDate = projectStartDate;
		this.projectEndDate = projectEndDate;
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
}
