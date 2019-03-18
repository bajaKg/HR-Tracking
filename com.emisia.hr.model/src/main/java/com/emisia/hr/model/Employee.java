package com.emisia.hr.model;

import java.util.Date;

public class Employee {

    private int _id;
    private String _firstName;
    private String _lastName;
    
    private Date _startDate;
    private String _emailAddress;
    private String _phone;
    private String _education;
    private String _workExpiriance;
    private String _interests;
    private String _cvAddress; // addresa uploadovanog fajla
    private Project _project;
    private String _position;
    private Date _timeOfDisable;
    private boolean _enabled;

    public Employee(String firstName, String lastName, Date startDate,
	    String emailAddress, String phone, String education,
	    String workExpiriance, String interests, String cvAddress,
	    Project project, String position, Date timeOfDisable,
	    boolean enabled) {
	super();
	_firstName = firstName;
	_lastName = lastName;
	_startDate = startDate;
	_emailAddress = emailAddress;
	_phone = phone;
	_education = education;
	_workExpiriance = workExpiriance;
	_interests = interests;
	_cvAddress = cvAddress;
	_project = project;
	_position = position;
	_timeOfDisable = timeOfDisable;
	_enabled = enabled;
    }

    public int getId() {
	return _id;
    }

    public void setId(int id) {
	_id = id;
    }

    public String getFirstName() {
	return _firstName;
    }

    public void setFirstName(String firstName) {
	_firstName = firstName;
    }

    public String getLastName() {
	return _lastName;
    }

    public void setLastName(String lastName) {
	_lastName = lastName;
    }

    public Date getStartDate() {
	return _startDate;
    }

    public void setStartDate(Date startDate) {
	_startDate = startDate;
    }

    public String getEmailAddress() {
	return _emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
	_emailAddress = emailAddress;
    }

    public String getPhone() {
	return _phone;
    }

    public void setPhone(String phone) {
	_phone = phone;
    }

    public String getEducation() {
	return _education;
    }

    public void setEducation(String education) {
	_education = education;
    }

    public String getWorkExpiriance() {
	return _workExpiriance;
    }

    public void setWorkExpiriance(String workExpiriance) {
	_workExpiriance = workExpiriance;
    }

    public String getInterests() {
	return _interests;
    }

    public void setInterests(String interests) {
	_interests = interests;
    }

    public String getCvAddress() {
	return _cvAddress;
    }

    public void setCvAddress(String cvAddress) {
	_cvAddress = cvAddress;
    }

    public Project getProject() {
	return _project;
    }

    public void setProject(Project project) {
	_project = project;
    }

    public String getPosition() {
	return _position;
    }

    public void setPosition(String position) {
	_position = position;
    }

    public boolean isEnabled() {
	return _enabled;
    }

    public void setEnabled(boolean enabled) {
	if (enabled == false) {
	    _timeOfDisable = new Date(); // dodljuje trenutno vreme i datum
					 // promenljivi _timeOfDisable
	}
	_enabled = enabled;
    }

    public Date getTimeOfDisable() {
	return _timeOfDisable;
    }

    public void setTimeOfDisable(Date timeOfDisable) {
	_timeOfDisable = timeOfDisable;
    }

}
