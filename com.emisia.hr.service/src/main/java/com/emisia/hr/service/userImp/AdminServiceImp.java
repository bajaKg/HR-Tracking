package com.emisia.hr.service.userImp;

import java.sql.SQLException;
import java.util.HashSet;
import com.emisia.hr.dao.userIMP.AdminDaoImp;
import com.emisia.hr.model.User;
import com.emisia.hr.service.user.AdminService;

public class AdminServiceImp implements AdminService {
    private static AdminDaoImp _admin;

    public AdminServiceImp() {
	_admin = new AdminDaoImp();
    }

    public HashSet<User> showAllUsers() {
	return _admin.showAllUsers();
    }

    public HashSet<User> showSearchedUsers(String term) {
	return _admin.showSearchedUsers(term);
    }

    public void editUsers(int id, String password, String role) {
	_admin.editUser(id, password, role);
    }

    public boolean addUser(String firstName, String lastName,
	    String userName, String password, String email, String role,
	    boolean enabled) throws SQLException {
	boolean addUserService = false;
	if (_admin.addUser(firstName, lastName, userName, password, email,
		role, enabled) == true)
	    addUserService = true;
	else
	    addUserService = false;
	return addUserService;
    }
    public String getRole(String userName, String password) throws SQLException{
	AdminDaoImp dao = new AdminDaoImp();
	String _role = null;
	_role = dao.findRole(userName, password);
	//if(_role != null) return _role;
	return _role;
    }
    public boolean loginFormFindUser(String _userName, String _password) {
	boolean _login = false;
	try {
	   if( AdminDaoImp.loginFormFindUsernamePassword(_userName, _password)==true)
	    _login = true;
	   else 
	       _login = false;
	} catch (SQLException e) {
	    e.printStackTrace();

	}

	return _login;
    }

    public void enableUser(int userId) {
	_admin.enableUser(userId);
    }

    public void disableUser(int userId) {
	_admin.disableUser(userId);
    }

    public User getUser(int userId) {
	return _admin.getUser(userId);
    }

    public void editEmployeExit(int id, String reasonLeaving) {
	_admin.editEmployeExit(id, reasonLeaving);
	
    }
}