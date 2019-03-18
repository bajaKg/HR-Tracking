package com.emisia.hr.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.emisia.hr.dao.userIMP.HRDaoImp;
import com.emisia.hr.model.*;

public class HRDaoTest {

    HRDaoImp hr = new HRDaoImp();

    @Before
    public void reset_JobPosts_Table() {
	H2DataBaseUtils.executeUpdate("DROP TABLE IF EXISTS JOB_POSTS");

	H2DataBaseUtils
		.createTable("CREATE TABLE IF NOT EXISTS JOB_POSTS(ID INT PRIMARY KEY AUTO_INCREMENT,JOB_POSITION VARCHAR(255),"
			+ " PROJECT_NAME VARCHAR(255), JOB_TYPE VARCHAR(255), NO_OF_POSITIONS INT, CLOSING_DATE DATE,"
			+ " DESCRIPTION VARCHAR(255));");

	H2DataBaseUtils
		.executeUpdate("INSERT INTO JOB_POSTS(JOB_POSITION, PROJECT_NAME,"
			+ " JOB_TYPE, NO_OF_POSITIONS, CLOSING_DATE,"
			+ " DESCRIPTION) VALUES('DEVELOPER','hrtracking','dd',4,NULL,'testOpis');");
    }
        
    @Before
    public void resetProjects_Employee_And_Table(){		
	H2DataBaseUtils.executeUpdate("DROP TABLE IF EXISTS PROJECTS");
	H2DataBaseUtils.createTable(
		    "CREATE TABLE IF NOT EXISTS PROJECTs(ID INT PRIMARY KEY AUTO_INCREMENT,PROJECT_NAME VARCHAR(255) NOT NULL,"
			    + "CLIENT_NAME VARCHAR(255) NOT NULL,PROJECT_START_DATE DATE NOT NULL, PROJECT_END_DATE DATE NOT NULL,"
			    + "PROJECT_DESCRIPTION VARCHAR(255) NOT NULL);");
	H2DataBaseUtils.executeUpdate("INSERT INTO PROJECTs(PROJECT_NAME, CLIENT_NAME,PROJECT_START_DATE, PROJECT_END_DATE, PROJECT_DESCRIPTION) VALUES('hrtacking2','milos', '2012-07-24', '2012-07-24', 'Opis');");	
	
	H2DataBaseUtils.executeUpdate("DROP TABLE IF EXISTS EMPLOYEE");
	H2DataBaseUtils.createTable(
		"CREATE TABLE IF NOT EXISTS EMPLOYEE(ID INT PRIMARY KEY AUTO_INCREMENT,FIRSTNAME VARCHAR(255) NOT NULL,"
			+ "LASTNAME VARCHAR(255) NOT NULL,STARTDATE DATE NOT NULL, EMAIL VARCHAR(255) NOT NULL,"
			+ "PHONE VARCHAR(255) NOT NULL,"
			+ "EDUCATION VARCHAR(255) NOT NULL,WORKEXPIRIANCE VARCHAR(255) NOT NULL,"
			+ "POSITION VARCHAR(255) NOT NULL,INTERESTS VARCHAR(255) NOT NULL,CVADDRESS VARCHAR(255) NOT NULL,"
			+ "TIMEOFDISABLE DATE,ENABLED BOOLEAN, PROJECT VARCHAR(255));");	
	H2DataBaseUtils.executeUpdate("INSERT INTO EMPLOYEE(FIRSTNAME, LASTNAME, STARTDATE, EMAIL, PHONE, EDUCATION, "
		+ "WORKEXPIRIANCE, INTERESTS, CVADDRESS, ENABLED, POSITION, TIMEOFDISABLE, PROJECT) VALUES('MILOS','BAJIC','2012-07-24','','','','','','',TRUE,'','2012-07-24', null);");	
			
	H2DataBaseUtils.executeUpdate("DROP TABLE IF EXISTS JOB_CANDIDATE");
	H2DataBaseUtils.createTable(
		"CREATE TABLE IF NOT EXISTS JOB_CANDIDATE(ID INT PRIMARY KEY AUTO_INCREMENT,FIRSTNAME VARCHAR(255) NOT NULL,"
			+ "LASTNAME VARCHAR(255) NOT NULL,JOB_POSITION VARCHAR(255) NOT NULL, EMAIL VARCHAR(255) NOT NULL,"
			+ "PHONE VARCHAR(255) NOT NULL,"
			+ "EDUCATION VARCHAR(255) NOT NULL, WORKEXPIRIANCE VARCHAR(255) NOT NULL,"
			+ "HOBBIES VARCHAR(255), MOBILE VARCHAR(255), ADDRESS VARCHAR(255) NOT NULL,"
			+ "GENDER VARCHAR(30), CV_ADDRESS VARCHAR(255));");
    }       
    
    @Test
    public void testCreateTableJobCandidates() {
	boolean created = hr.createJobCandidatesTable();
	assertTrue(created);
    }

    @Test
    public void testCreateTableEmployee() {	
	boolean created = hr.createEmployeeTable();
	assertTrue(created);	
    }

    
    @Test
    public void testCreateTableTeam() {
//	 H2DataBaseUtils.createTable("DROP TABLE TEAM");
	boolean created = hr.createTeamTable();
	assertTrue(created);
    }
    
    @Test
    public void testAddNewEmployee() {
	// HrDaoImpl hr = new HrDaoImpl();
	boolean added = hr.addNewEmployee("Milos", "Bajic", new Date(),
		"bajic@live.com", "+381 64 487 02 79", "Prva tehnicka",
		"Nikakvo", "Svasta", "softwer arhitect", new Date(),
		"/temp/cvs", true);
	assertTrue(added);
    }

    @Test
    public void testGetAllEmployees() {
	HashSet<EmployeeDto> employees = hr.getAllEmployees();
	assertNotNull(employees);
	assertFalse(employees.isEmpty());
			
	//assertEquals(1, employees.size());
	
	Iterator<EmployeeDto> itr = employees.iterator();
	EmployeeDto employee = (EmployeeDto) itr.next();
	assertEquals("MILOS", employee.getFirstName());
	assertEquals("BAJIC", employee.getLastName());
	assertEquals("hrtacking2", employee.getProjectName());
		
    }

    @Test
    public void createProjectTable() {
    	
	assertTrue(hr.createProjectsTable());
    }

    @Test
    public void addNewProject() {
	//hr.addNewProject("p1", "k1", new Date(), new Date(), "bas lepo", null);	
	hr.addNewProject("imicodemaze", "mileva", new Date() , new Date(), "los projekat");			
    }

    @Test
    public void showAllProjects() {

    }

    @Test
    public void testCreateJobPostsTable() {
	H2DataBaseUtils.createTable("DROP TABLE IF EXISTS JOB_POSTS");
	boolean created = hr.createJobPostTable();
	assertTrue(created);
    }

    @Test
    public void testAddNewJobPost() {

    	hr.addNewJobPost("developer", "hrtracking", "/", 4, new Date(), "opis");
	
    }

    @Test
    public void testGetAllJobPosts() {
	HashSet<JobPost> jobPosts = hr.showAllJobPosts();
	assertFalse(jobPosts.isEmpty());
	assertEquals(1, jobPosts.size());
	
	Iterator<JobPost> itr = jobPosts.iterator();
	JobPost jobPost = (JobPost) itr.next();
	assertEquals("testOpis", jobPost.getJobDescription());
	assertEquals("DEVELOPER", jobPost.getJobPosition());
	assertEquals("hrtracking", jobPost.getProjectName());
    }
            
    
    @Test
    public void testaddNewJobCandidate(){
	boolean added = hr.addNewJobCandidate("Milos", "Bajic", "Muski", "Kneza Mihaila", "super@seup.com", "123", "321", "PMF", "Nikakvo", "Nista", "developer", "/cv/");
	assertTrue(added);
    }
    
    @Test
    public void testEnableDisableEmployee(){
	hr.disableEmployee(1);
	assertEquals(false, hr.isEnabled(1));
	hr.enableEmployee(1);
	assertEquals(true, hr.isEnabled(1));
    }
    
    
    

}