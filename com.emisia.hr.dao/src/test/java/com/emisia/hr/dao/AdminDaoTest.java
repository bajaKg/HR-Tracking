package com.emisia.hr.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.emisia.hr.dao.userIMP.AdminDaoImp;

public class AdminDaoTest {
    AdminDaoImp admin = new AdminDaoImp();
    
    
    @Test
    public void addUser() throws SQLException {
	H2DataBaseUtils.executeUpdate("DROP TABLE IF EXISTS USER");
	H2DataBaseUtils.createTables();
	admin.addUser("admin", "admin", "admin", "admin", "admin", "admin", true);
	AdminDaoImp admin = new AdminDaoImp();
	AdminDaoImp.addUserTableTest();
	assertTrue(admin.addUser("Aleksandar", "Stojanovic", "aca034", "mr99", "ar#1", "Employee", true));

    }

    @Ignore
    public void _addEmployeeExit() {
	assertTrue(AdminDaoImp.addUserEmployeeExitTable());
    }

    @Ignore
    public void loginFormFindUsernamePassword() throws SQLException {

	assertTrue(AdminDaoImp.loginFormFindUsernamePassword("mrakonjac", "mrr"));
    }

    @Test
    public void testShowAllUsers() {

	AdminDaoImp admin = new AdminDaoImp();

	admin.showAllUsers();
    }

    @Test
    public void findRole() throws SQLException {
	AdminDaoImp dao = new AdminDaoImp();
	System.out.println(dao.findRole("biceFalse1222456", "gs#42"));
    }

    @Test
    public void testDisableAndEnable() {
	AdminDaoImp admin = new AdminDaoImp();
	admin.disableUser(1);
	assertFalse(admin.getUser(1).isEnabled());
	admin.enableUser(1);
	assertTrue(admin.getUser(1).isEnabled());
    }

    @Test
    public void editUser() {
	AdminDaoImp admin = new AdminDaoImp();
	admin.editUser(1, "skk", "hr");
    }

}