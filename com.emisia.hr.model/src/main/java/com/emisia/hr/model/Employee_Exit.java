package com.emisia.hr.model;

import java.util.Date;

public class Employee_Exit {

	 private int _id;
	 private String _firstName;
	 private String _lastName;
	 private String _userName;
	 private Date _timeOfDisable;
	 private String _reasonLeaving;
	 private boolean _enabled;
	 
	 public boolean isEnabled() {
	    return _enabled;
	}

	public void setEnabled(boolean enabled) {
	    _enabled = enabled;
	}

	public Employee_Exit(Integer id,String firstName, String lastName, String _userName, Date timeOfDisable, String reasonLeaving){
		 this._id = id;
		 this._firstName = firstName;
		 this._lastName = lastName;
		 this._timeOfDisable = timeOfDisable;
		 this._reasonLeaving = reasonLeaving;
		 this._userName = _userName;
	 }

	public String getUserName() {
	    return _userName;
	}

	public void setUserName(String userName) {
	    _userName = userName;
	}

	public int getId() {
		return _id;
	}

	public void setId(int _id) {
		this._id = _id;
	}

	public String getFirstName() {
		return _firstName;
	}

	public void setFirstName(String _firstName) {
		this._firstName = _firstName;
	}

	public String getLastName() {
		return _lastName;
	}

	public void setLastName(String _lastName) {
		this._lastName = _lastName;
	}

	public Date getTimeOfDisable() {
		return _timeOfDisable;
	}

	public void setTimeOfDisable(Date _timeOfDisable) {
		this._timeOfDisable = _timeOfDisable;
	}

	public String getReasonLeaving() {
		return _reasonLeaving;
	}

	public void setReasonLeaving(String _reasonLeaving) {
		this._reasonLeaving = _reasonLeaving;
	}
}
