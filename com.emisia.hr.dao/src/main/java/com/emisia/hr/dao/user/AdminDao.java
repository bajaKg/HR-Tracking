package com.emisia.hr.dao.user;

import java.sql.SQLException;
import java.util.HashSet;

import com.emisia.hr.model.User;

public interface AdminDao {
    public HashSet<User> showAllUsers();

    public void editUser(int id, String password, String role);

    public boolean addUser(String firstName, String lastName, String userName,
	    String password, String email, String role, boolean enable)
	    throws SQLException;

    public void enableUser(int userId);

    public void disableUser(int userId);

    public HashSet<User> showSearchedUsers(String term);
}