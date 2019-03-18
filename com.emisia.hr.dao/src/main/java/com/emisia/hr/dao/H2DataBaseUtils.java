package com.emisia.hr.dao;

import com.emisia.hr.dao.userIMP.HRDaoImp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class H2DataBaseUtils {

    private static final String DB_NAME = "HrTracking";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String USER_DIR = "user.home";
    private static final String OS_SEPARATOR = "file.separator";

    static {
	H2DataBaseUtils.createTables();
    }

    // Stampa ime i prezime user-a. Isprobavao sam addUser *Milos Rakonjac
    public static void dropUser() {
	Connection _conn = null;
	PreparedStatement _preparedStatement = null;

	_conn = getConnection();
	if (_conn != null) {
	    try {
		_preparedStatement = _conn.prepareStatement("DROP TABLE USER");
		_preparedStatement.executeUpdate();
		_preparedStatement.close();

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		if (_preparedStatement != null)
		    try {
			_preparedStatement.close();
		    } catch (SQLException ignore) {

		    }

		if (_conn != null)
		    try {
			_conn.close();
		    } catch (SQLException ignore) {

		    }
	    }
	}

    }

    public static void printRecords() throws SQLException {
	Connection _conn = null;
	PreparedStatement _preparedStatement = null;

	String _sqlQuery = "SELECT* FROM USER";
	_conn = getConnection();
	if (_conn != null) {
	    try {

		_preparedStatement = _conn.prepareStatement(_sqlQuery);

		ResultSet rs = _preparedStatement.executeQuery();

		while (rs.next()) {
		    int a = rs.getInt("ID");

		    String _firstaName = rs.getString("FIRSTNAME");
		    String _lastName = rs.getString("LASTNAME");
		    String _userName = rs.getString("USERNAME");
		    String _password = rs.getString("PASSWORD");
		    boolean _enable = rs.getBoolean("ENABLED");

		    System.out.println("aa" + a);
		    System.out.println("First name: " + _firstaName);
		    System.out.println("Last name: " + _lastName);
		    System.out.println("Username: " + _userName);
		    System.out.println("Password: " + _password);
		    System.out.println("Enable: " + _enable);

		}
	    } catch (SQLException se) {
		System.out.println(se.getMessage());
	    } finally {
		if (_preparedStatement != null) {
		    _preparedStatement.close();
		}
		if (_conn != null) {
		    _conn.close();
		}
	    }
	}

	// System.out.println("userdir "+_userDir+" oserparator "+_osSeparator);
    }

    public static synchronized Connection getConnection() {
	Connection _conn = null;

	try {
	    Class.forName("org.h2.Driver");
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}

	String _userDir = System.getProperty(USER_DIR);
	String _osSeparator = System.getProperty(OS_SEPARATOR);

	try {
	    _conn = DriverManager.getConnection(
		    "jdbc:h2:file:" + _userDir + _osSeparator + "hrtracking" + _osSeparator + DB_NAME, DB_USER,
		    DB_PASSWORD);

	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return _conn;
    }

    public static synchronized boolean createTable(String query) {
	boolean _createdTable = false;

	Connection _conn = null;
	PreparedStatement _preparedStatement = null;

	_conn = getConnection();

	if (_conn != null) {
	    try {
		_preparedStatement = _conn.prepareStatement(query);
		_preparedStatement.executeUpdate();
		_preparedStatement.close();
		_createdTable = true;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		if (_preparedStatement != null)
		    try {
			_preparedStatement.close();
		    } catch (SQLException ignore) {

		    }

		if (_conn != null)
		    try {
			_conn.close();
		    } catch (SQLException ignore) {

		    }
	    }
	}

	return _createdTable;
    }

    public static boolean createTables() {
	boolean _createTablesOrders = false;
	try {
	    createTable(
		    "CREATE TABLE IF NOT EXISTS USER(ID INT PRIMARY KEY AUTO_INCREMENT,FIRSTNAME VARCHAR(255) NOT NULL,"
			    + "LASTNAME VARCHAR(255) NOT NULL,USERNAME VARCHAR(255) NOT NULL,PASSWORD VARCHAR(255) NOT NULL,"
			    + "EMAIL VARCHAR(255) NOT NULL,ROLE VARCHAR(255) NOT NULL, ENABLED BOOLEAN );");
	    _createTablesOrders = true;
	} catch (Exception e) {
	    System.out.println("Exception table USER " + e.getMessage());

	}
	try {
	    createTable(
		    "CREATE TABLE IF NOT EXISTS EMPLOYEE_EXIT(ID INT PRIMARY KEY AUTO_INCREMENT, FIRSTNAME VARCHAR(255) NOT NULL,"
			    + " LASTNAME VARCHAR(255) NOT NULL, USERNAME VARCHAR(255) NULL, TIME_OF_DISABLING DATE NOT NULL, REASON_LEAVING VARCHAR(255) NULL )");

	} catch (Exception e) {
	    System.out.println("Exception table EMPLOYEE_EXIT " + e.getMessage());
	}

	createTable(
		"CREATE TABLE IF NOT EXISTS EMPLOYEE(ID INT PRIMARY KEY AUTO_INCREMENT,FIRSTNAME VARCHAR(255) NOT NULL,"
			+ "LASTNAME VARCHAR(255) NOT NULL,STARTDATE DATE NOT NULL,EMAIL VARCHAR(255) NOT NULL,"
			+ "PHONE VARCHAR(255) NOT NULL,"
			+ "EDUCATION VARCHAR(255) NOT NULL,WORKEXPIRIANCE VARCHAR(255) NOT NULL,"
			+ "POSITION VARCHAR(255) NOT NULL,INTERESTS VARCHAR(255) NOT NULL,CVADDRESS VARCHAR(255) NOT NULL,"
			+ "TIMEOFDISABLE DATE,ENABLED BOOLEAN, PROJECT VARCHAR(255));");	

	createTable("CREATE TABLE IF NOT EXISTS JOB_POSTS(ID INT PRIMARY KEY AUTO_INCREMENT,JOB_POSITION VARCHAR(255),"
		+ " PROJECT_NAME VARCHAR(255), JOB_TYPE VARCHAR(255), NO_OF_POSITIONS INT, CLOSING_DATE DATE,"
		+ " DESCRIPTION VARCHAR(255), CV_ADDRESS VARCHAR(255));");

	createTable(
		"CREATE TABLE IF NOT EXISTS JOB_CANDIDATE(ID INT PRIMARY KEY AUTO_INCREMENT,FIRSTNAME VARCHAR(255) NOT NULL,"
			+ "LASTNAME VARCHAR(255) NOT NULL,JOB_POSITION VARCHAR(255) NOT NULL, EMAIL VARCHAR(255) NOT NULL,"
			+ "PHONE VARCHAR(255) NOT NULL,"
			+ "EDUCATION VARCHAR(255) NOT NULL, WORKEXPIRIANCE VARCHAR(255) NOT NULL,"
			+ "HOBBIES VARCHAR(255), MOBILE VARCHAR(255), ADDRESS VARCHAR(255) NOT NULL,"
			+ "GENDER VARCHAR(30), CV_ADDRESS VARCHAR(255));");

	return _createTablesOrders;
    }

    public static synchronized boolean executeUpdate(String query) {
	boolean _createdTable = false;

	Connection _conn = null;
	PreparedStatement _preparedStatement = null;

	_conn = getConnection();

	if (_conn != null) {
	    try {
		_preparedStatement = _conn.prepareStatement(query);
		_preparedStatement.executeUpdate();
		_preparedStatement.close();
		_createdTable = true;

	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		if (_preparedStatement != null)
		    try {
			_preparedStatement.close();
		    } catch (SQLException ignore) {

		    }

		if (_conn != null)
		    try {
			_conn.close();
		    } catch (SQLException ignore) {

		    }
	    }
	}

	return _createdTable;
    }
}
