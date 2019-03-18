package com.emisia.hr.model;

public class EmployeeDto {

    private int _id;
    private String _firstName;
    private String _lastName;
    private String _projectName;
    private String _positon;
    private boolean _enabled;

    public EmployeeDto(int id, String firstName, String lastName,
	    String projectName, String positon, boolean enabled) {
	super();
	_id = id;
	_firstName = firstName;
	_lastName = lastName;
	_projectName = projectName;
	_positon = positon;
	_enabled = enabled;
    }

    public int getId() {
	return this._id;
    }
    
    public void setId(int id){
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

    public String getProjectName() {
	return _projectName;
    }

    public void setProjectName(String projectName) {
	_projectName = projectName;
    }

    public String getPositon() {
	return _positon;
    }

    public void setPositon(String positon) {
	_positon = positon;
    }

    public boolean getEnabled() {
	return _enabled;
    }

    public void setEnabled(boolean enabled) {
	_enabled = enabled;
    }

	public String get_projectName() {
		return _projectName;
	}

	public void set_projectName(String _projectName) {
		this._projectName = _projectName;
	}
}
