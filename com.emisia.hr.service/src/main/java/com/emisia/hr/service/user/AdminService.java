package com.emisia.hr.service.user;

import java.sql.SQLException;
import java.util.HashSet;

import com.emisia.hr.model.User;

public interface AdminService {

	    public HashSet<User> showAllUsers();

	    public HashSet<User> showSearchedUsers(String term);
	    
	    public void editUsers(int id, String password, String role);
	    public void editEmployeExit(int id, String reasonLeaving);

	    public boolean addUser(String firstName, String lastName,
		    String userName, String password, String email, String role,
		    boolean enabled) throws SQLException;
		    
	    public String getRole(String userName, String password) throws SQLException;
	    
	    public boolean loginFormFindUser(String _userName, String _password);

	    public void enableUser(int userId);

	    public void disableUser(int userId);

	    public User getUser(int userId) ;
}