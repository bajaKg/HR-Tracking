package com.emisia.hr.model;

import java.util.Date;

public class JobPost {
    private int _id;
    private String _jobPosition;
    private String _projectName;
    private String _jobType;
    private int _noOfPositions;
    private Date _closingDate;
    private String _jobDescription;    
    
    public JobPost(int id, String jobPosition, String projectName,
	    String jobType, int noOfPositions, Date closingDate,
	    String jobDescription) {
	super();
	_id = id;
	_jobPosition = jobPosition;
	_projectName = projectName;
	_jobType = jobType;
	_noOfPositions = noOfPositions;
	_closingDate = closingDate;
	_jobDescription = jobDescription;	
    }
    
    public int getId() {
        return _id;
    }
    public void setId(int id) {
        _id = id;
    }
    public String getJobPosition() {
        return _jobPosition;
    }
    public void setJobPosition(String jobPosition) {
        _jobPosition = jobPosition;
    }
    public String getProjectName() {
        return _projectName;
    }
    public void setProjectName(String projectName) {
        _projectName = projectName;
    }
    public String getJobType() {
        return _jobType;
    }
    public void setJobType(String jobType) {
        _jobType = jobType;
    }
    public int getNoOfPositions() {
        return _noOfPositions;
    }
    public void setNoOfPositions(int noOfPositions) {
        _noOfPositions = noOfPositions;
    }
    public Date getClosingDate() {
        return _closingDate;
    }
    public void setClosingDate(Date closingDate) {
        _closingDate = closingDate;
    }
    public String getJobDescription() {
        return _jobDescription;
    }
    public void setJobDescription(String jobDescription) {
        _jobDescription = jobDescription;
    }            
    
}
