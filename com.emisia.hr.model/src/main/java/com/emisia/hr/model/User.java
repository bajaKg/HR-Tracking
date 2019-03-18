package com.emisia.hr.model;

public class User {

    private int _id;
    private String _firstName;
    private String _lastName;
    private String _userName;
    private String _password;
    private String _email;
    private UserRole _role;
    private boolean _enabled;//da li se ovo razlikuje od enabled u Employee?

    public User(int id, String firstName, String lastName, String userName,
	    String password, String email, String role, boolean enable) {
	this._id = id;
	this._firstName = firstName;
	this._lastName = lastName;
	this._userName = userName;
	this._password = password;
	this._email = email;
	this._role = UserRole.valueOf(role.toUpperCase());
	this._enabled = enable;
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

    public String getUserName() {
        return _userName;
    }

    public void setUserName(String userName) {
        _userName = userName;
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password) {
        _password = password;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String email) {
        _email = email;
    }

    public UserRole getRole() {
        return _role;
    }

    public void setRole(UserRole role) {
        _role = role;
    }

    public boolean isEnabled() {
        return _enabled;
    }

    public void setEnabled(boolean enable) {
        _enabled = enable;
    }

    

}
