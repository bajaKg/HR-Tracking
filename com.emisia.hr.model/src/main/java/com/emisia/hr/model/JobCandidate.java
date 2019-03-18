package com.emisia.hr.model;

public class JobCandidate {

    private int _id;
    private String _firstName;
    private String _lastName;
    private String _email;
    private String _jobPosition;
    private String _cv;
    
    public JobCandidate(int id, String firstName, String lastName, String email, String jobPosition, String cv){
	this._id = id;
	this._firstName = firstName;
	this._lastName = lastName;
	this._email = email;
	this._jobPosition = jobPosition;
	this._cv = cv;
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

    public String getEmail() {
        return _email;
    }

    public void setEmail(String email) {
        _email = email;
    }

    public String getJobPosition() {
        return _jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        _jobPosition = jobPosition;
    }

	public String getcv() {
		return _cv;
	}

	public void setcv(String _cv) {
		this._cv = _cv;
	}
    
}
