package com.emisia.hr.service;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.emisia.hr.service.userImp.AdminServiceImp;

public class AdminServiceTest {

    @Test 
    public void addUser() throws SQLException {
	assertTrue(new AdminServiceImp().addUser("Petrol", "Kole ", "biceFalse1222456", "gs#42",
		"perke@live.com ", "admin", true));
    }

    @Test 
    public void loginFormFindUser() {
	assertTrue(new AdminServiceImp().loginFormFindUser("biceFalse1", "gs#42"));
    }
   
    
    @Test
    public void testDisableAndEnableUser(){
	AdminServiceImp as = new AdminServiceImp();
	int userId = 1;
	as.disableUser(userId);
	assertFalse(as.getUser(userId).isEnabled());
	as.enableUser(userId);
	assertTrue(as.getUser(userId).isEnabled());
    }
}