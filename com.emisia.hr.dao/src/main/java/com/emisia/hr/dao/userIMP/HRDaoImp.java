package com.emisia.hr.dao.userIMP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import com.emisia.hr.dao.H2DataBaseUtils;
import com.emisia.hr.dao.user.HRDao;
import com.emisia.hr.model.Employee;
import com.emisia.hr.model.EmployeeDto;
import com.emisia.hr.model.Employee_Exit;
import com.emisia.hr.model.JobCandidate;
import com.emisia.hr.model.JobPost;
import com.emisia.hr.model.Project;

public class HRDaoImp implements HRDao {

    H2DataBaseUtils h = new H2DataBaseUtils(); //Onaj koji je ovo radio neka izbaci instanciranje ove klase jer je staticka
   

    public boolean createEmployeeTable() {
	boolean created = H2DataBaseUtils.createTable(
		"CREATE TABLE IF NOT EXISTS EMPLOYEE(ID INT PRIMARY KEY AUTO_INCREMENT,FIRSTNAME VARCHAR(255) NOT NULL,"
			+ "LASTNAME VARCHAR(255) NOT NULL,STARTDATE DATE NOT NULL,EMAIL VARCHAR(255) NOT NULL,"
			+ "PHONE VARCHAR(255) NOT NULL,"
			+ "EDUCATION VARCHAR(255) NOT NULL,WORKEXPIRIANCE VARCHAR(255) NOT NULL,"
			+ "POSITION VARCHAR(255) NOT NULL,INTERESTS VARCHAR(255) NOT NULL,CVADDRESS VARCHAR(255) NOT NULL,"
			+ "TIMEOFDISABLE DATE,ENABLED BOOLEAN, PROJECT VARCHAR(255));");
	return created;
    }
    
    public boolean createProjEmplTable(){
	boolean created = H2DataBaseUtils.createTable("CREATE TABLE IF NOT EXISTS PROJEMPL(EMPLOYEE_ID INT, PROJECT_ID INT,"
		+ "foreign key (EMPLOYEE_ID) references EMPLOYEE(ID), foreign key(PROJECT_ID) references PROJECTs(ID), PRIMARY KEY (EMPLOYEE_ID, PROJECT_ID));");
	return created;
    }

    public HashSet<EmployeeDto> getAllEmployees() {
	Connection _con;
	Statement _statement = null;
	_con = H2DataBaseUtils.getConnection();
	HashSet<EmployeeDto> _allEmployees = new HashSet<EmployeeDto>();

	if (_con != null) {

	    try {
		_statement = _con.createStatement();
		
		ResultSet rs = _statement.executeQuery("SELECT EMPLOYEE.id, EMPLOYEE.firstName, EMPLOYEE.lastName, P.PROJECT_NAME, EMPLOYEE.position, EMPLOYEE.enabled "
			+ "FROM (SELECT PROJECTS.PROJECT_NAME, PROJEMPL.EMPLOYEE_ID "				
				+ "FROM PROJEMPL "
				+ "LEFT JOIN PROJECTS ON PROJEMPL.PROJECT_ID = PROJECTS.ID) P "
			+ "RIGHT JOIN EMPLOYEE ON P.EMPLOYEE_ID = EMPLOYEE.ID");
		
		while (rs.next()) {
		    _allEmployees.add(new EmployeeDto(rs.getInt("ID"), rs.getString("FIRSTNAME"),
			    rs.getString("LASTNAME"), rs.getString("PROJECT_NAME"), rs.getString("POSITION"), rs.getBoolean("ENABLED")));
		}
	    } catch (SQLException se) {
		System.out.println(se.getMessage());
	    } finally {
		if (_statement != null) {
		    try {
			_statement.close();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
		if (_con != null) {
		    try {
			_con.close();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
	    }
	}

	return _allEmployees;
    }


    public boolean addNewEmployee(String firstName, String lastName, Date startDate, String emailAddress, String phone,
	    String education, String workExpiriance, String interests, String position, Date timeOfDisable,
	    String cvAddress, boolean enabled) {

	boolean _employeeAdded = false;
	Connection _conn = H2DataBaseUtils.getConnection();
	PreparedStatement _preparedStatement = null;
	java.sql.Date sqlDate;
	String _sqlQuery = "INSERT INTO EMPLOYEE(FIRSTNAME, LASTNAME, STARTDATE, EMAIL, PHONE, EDUCATION, "
		+ "WORKEXPIRIANCE, INTERESTS, CVADDRESS, ENABLED, POSITION, TIMEOFDISABLE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";

	if (_conn != null) {
	    try {
		_conn = H2DataBaseUtils.getConnection();
		_preparedStatement = _conn.prepareStatement(_sqlQuery);

		_preparedStatement.setString(1, firstName);
		_preparedStatement.setString(2, lastName);
		if(startDate!=null){
		    sqlDate = new java.sql.Date(startDate.getTime());
		}
		else 
		    sqlDate = null;
		_preparedStatement.setDate(3, sqlDate);
		_preparedStatement.setString(4, emailAddress);
		_preparedStatement.setString(5, phone);
		_preparedStatement.setString(6, education);
		_preparedStatement.setString(7, workExpiriance);
		_preparedStatement.setString(8, interests);
		_preparedStatement.setString(9, cvAddress);
		_preparedStatement.setBoolean(10, enabled);
		_preparedStatement.setString(11, position);
		_preparedStatement.setDate(12, null);

		_preparedStatement.executeUpdate();
		_employeeAdded = true;
	    } catch (Exception e) {
		e.printStackTrace();

	    } finally {
		if (_preparedStatement != null) {
		    try {
			_preparedStatement.close();
			if (_conn != null) {
			    _conn.close();
			}
		    } catch (Exception e2) {
			e2.printStackTrace();
		    }
		}
	    }

	}

	return _employeeAdded;
    }

    public boolean createProjectsTable() {
	boolean _createTablesOrders = false;
	try {
	    H2DataBaseUtils.createTable(
		    "CREATE TABLE IF NOT EXISTS PROJECTs(ID INT PRIMARY KEY AUTO_INCREMENT,PROJECT_NAME VARCHAR(255) NOT NULL,"
			    + "CLIENT_NAME VARCHAR(255) NOT NULL,PROJECT_START_DATE DATE NOT NULL,PROJECT_END_DATE DATE NOT NULL,"
			    + "PROJECT_DESCRIPTION VARCHAR(255) NOT NULL);");
	    _createTablesOrders = true;
	} catch (Exception e) {
	    e.printStackTrace();

	}

	return _createTablesOrders;
    }

    public HashSet<Project> showAllProjects() {
	Connection _con;
	Statement _statement = null;
	_con = H2DataBaseUtils.getConnection();
	HashSet<Project> _allProjects = new HashSet<Project>();

	if (_con != null) {

	    try {
		_statement = _con.createStatement();

		ResultSet rs = _statement.executeQuery("SELECT* FROM PROJECTS");

		while (rs.next()) {
		    _allProjects.add(new Project(rs.getInt("ID"), rs.getString("PROJECT_NAME"),
			    rs.getString("CLIENT_NAME"), rs.getDate("PROJECT_START_DATE"),
			    rs.getDate("PROJECT_END_DATE"), rs.getString("PROJECT_DESCRIPTION")));
		}
	    } catch (SQLException e) {
		e.printStackTrace();
	    } finally {
		if (_statement != null) {
		    try {
			_statement.close();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
		if (_con != null) {
		    try {
			_con.close();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
	    }
	}

	return _allProjects;
    }

    public void addNewProject(String projectName, String clientName, Date projectStartDate, Date projectEndDate,
	    String projectDescription) {
	Connection _conn = null;
	PreparedStatement _preparedStatement = null;

	String _sqlQuery = "INSERT INTO PROJECTS(PROJECT_NAME, CLIENT_NAME, PROJECT_START_DATE, PROJECT_END_DATE, PROJECT_DESCRIPTION) VALUES(?,?,?,?,?);";
	_conn = H2DataBaseUtils.getConnection();

	if (findProject(projectName) == -1) {

	    if (_conn != null) {
		try {
		    _conn = H2DataBaseUtils.getConnection();
		    _preparedStatement = _conn.prepareStatement(_sqlQuery);

		    _preparedStatement.setString(1, projectName);
		    _preparedStatement.setString(2, clientName);

		    java.sql.Date sqlDate = new java.sql.Date(projectStartDate.getTime());
		    _preparedStatement.setDate(3, sqlDate);
		    sqlDate = new java.sql.Date(projectEndDate.getTime());
		    _preparedStatement.setDate(4, sqlDate);
		    _preparedStatement.setString(5, projectDescription);

		    _preparedStatement.executeUpdate();
		} catch (Exception e) {
		    e.printStackTrace();

		} finally {
		    if (_preparedStatement != null) {
			try {
			    _preparedStatement.close();
			    if (_conn != null) {
				_conn.close();
			    }
			} catch (Exception e2) {
			    e2.printStackTrace();
			}
		    }
		}
	    }
	}
    }

    public HashSet<EmployeeDto> showAllProjectEmployees(int id) {
    	Connection _con;
    	Statement _statement = null;
    	_con = H2DataBaseUtils.getConnection();
    	HashSet<EmployeeDto> _allEmployees = new HashSet<EmployeeDto>();

    	if (_con != null) {

    	    try {
    		_statement = _con.createStatement();
    		
    		ResultSet rs = _statement.executeQuery("SELECT E.FIRSTNAME, E.LASTNAME, E.ID FROM EMPLOYEE AS E JOIN PROJECTS AS P ON E.PROJECT = P.PROJECT_NAME WHERE P.ID = " + id + "");
    		
    		while (rs.next()) {
    			
    			System.out.println(rs.getString("FIRSTNAME") + " " + rs.getString("LASTNAME") + " skk");
    		    _allEmployees.add(new EmployeeDto(rs.getInt("ID"), rs.getString("FIRSTNAME"),
    			    rs.getString("LASTNAME"), null, null, true));
    		}
    	    } catch (SQLException se) {
    		System.out.println(se.getMessage());
    	    } finally {
    		if (_statement != null) {
    		    try {
    			_statement.close();
    		    } catch (SQLException e) {
    			e.printStackTrace();
    		    }
    		}
    		if (_con != null) {
    		    try {
    			_con.close();
    		    } catch (SQLException e) {
    			e.printStackTrace();
    		    }
    		}
    	    }
    	}

    	return _allEmployees;
	
    }

    public HashSet<EmployeeDto> showAllEmployees() {
	Connection _con;
	Statement _statement = null;
	_con = H2DataBaseUtils.getConnection();
	HashSet<EmployeeDto> _allEmployees = new HashSet<EmployeeDto>();

	if (_con != null) {

	    try {
		_statement = _con.createStatement();

		ResultSet rs = _statement.executeQuery("SELECT* FROM EMPLOYEE");

		while (rs.next()) {
		    _allEmployees
			    .add(new EmployeeDto(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs .getString("PROJECT"), rs.getString("POSITION"), rs.getBoolean("ENABLED")));
		}
	    } catch (SQLException e) {
		e.printStackTrace();
	    } finally {
		if (_statement != null) {
		    try {
			_statement.close();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
		if (_con != null) {
		    try {
			_con.close();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
	    }
	}
	return _allEmployees;
    }

    public static synchronized boolean ExecuteYourQuery(String query) {

	boolean _executeYourQuery = false;

	Connection _conn = H2DataBaseUtils.getConnection();

	if (_conn != null) {

	    PreparedStatement preparedStatement = null;
	    try {
		preparedStatement = _conn.prepareStatement(query);
		preparedStatement.executeUpdate();
		_executeYourQuery = true;
	    } catch (SQLException eSQL) {
		System.out.println("Error in ExecuteYourQuery -HRDaoLmp- " + eSQL.getMessage());
	    } finally {
		if (preparedStatement != null) {
		    try {
			preparedStatement.close();
			if (_conn != null)
			    _conn.close();
		    } catch (SQLException eSQL) {
			System.out
				.println("Error in ExecuteYourQuery -HRDaoLmp--Error execute query or connection " + eSQL.getMessage());
		    }
		}
	    }
	}
	return _executeYourQuery;
    }
    public static synchronized boolean insertTable(String query) {

	boolean insertTable = false;

	Connection _conn = H2DataBaseUtils.getConnection();

	if (_conn != null) {

	    PreparedStatement preparedStatement = null;
	    try {
		preparedStatement = _conn.prepareStatement(query);
		preparedStatement.executeUpdate();
		insertTable = true;
	    } catch (SQLException eSQL) {
		System.out.println("Error in insertTable " + eSQL.getMessage());
	    } finally {
		if (preparedStatement != null) {
		    try {
			preparedStatement.close();
			if (_conn != null)
			    _conn.close();
		    } catch (SQLException eSQL) {
			System.out
				.println("Error in insertTable-Error execute query or connection " + eSQL.getMessage());
		    }
		}
	    }
	}
	return insertTable;
    }

    private static java.sql.Date getCurrentDate() {
   	java.util.Date today = new java.util.Date();
   	return new java.sql.Date(today.getTime());
       }
//WHERE USER.USERNAME NOT IN (SELECT EMPLOYEE_EXIT.USERNAME FROM EMPLOYEE_EXIT) AND
       public HashSet<Employee_Exit> showAllDisabledEmployees() {
   	Connection _con;
   	Statement _statement = null;
   	_con = H2DataBaseUtils.getConnection();
   	HashSet<Employee_Exit> _allDisabledEmployees = new HashSet<Employee_Exit>();
   	String insertSql = "INSERT INTO EMPLOYEE_EXIT" + "(FIRSTNAME, LASTNAME, USERNAME, TIME_OF_DISABLING, REASON_LEAVING)"
   		+ "SELECT FIRSTNAME, LASTNAME, USERNAME,'" + getCurrentDate()+"','"+" "
   		+ "' FROM USER WHERE NOT EXISTS (SELECT*  FROM EMPLOYEE_EXIT WHERE EMPLOYEE_EXIT.USERNAME=USER.USERNAME AND EMPLOYEE_EXIT.FIRSTNAME = USER.FIRSTNAME AND EMPLOYEE_EXIT.LASTNAME = USER.LASTNAME ) AND USER.ENABLED = '"
   		+ false + "'";
   	
	String insertSql1 = "INSERT INTO EMPLOYEE_EXIT" + "(FIRSTNAME, LASTNAME, TIME_OF_DISABLING, REASON_LEAVING)"
		+ "SELECT FIRSTNAME, LASTNAME,'" + getCurrentDate() + "','" + " "
		+ "' FROM EMPLOYEE WHERE EMPLOYEE.ENABLED = '"
		+ false + "'";
	String alterDrop = "DELETE FROM EMPLOYEE WHERE ENABLED = '"+false+"'";
   	insertTable(insertSql);
   	insertTable(insertSql1);
   	ExecuteYourQuery(alterDrop);
   	if (_con != null) {

   	    try {
   		_statement = _con.createStatement();

   		ResultSet rs = _statement.executeQuery("SELECT* FROM EMPLOYEE_EXIT");

   		while (rs.next()) {
   		    _allDisabledEmployees.add(new Employee_Exit(rs.getInt("ID"), rs.getString("FIRSTNAME"),
   			    rs.getString("LASTNAME"), rs.getString("USERNAME"), rs.getDate("TIME_OF_DISABLING"),
   			    rs.getString("REASON_LEAVING")));

   		}
   	    } catch (SQLException se) {
   		System.out.println(se.getMessage());
   	    } finally {
   		if (_statement != null) {
   		    try {
   			_statement.close();
   		    } catch (SQLException e) {
   			e.printStackTrace();
   		    }
   		}
   		if (_con != null) {
   		    try {
   			_con.close();
   		    } catch (SQLException e) {
   			e.printStackTrace();
   		    }
   		}
   	    }
   	}

   	return _allDisabledEmployees;
       }
    public HashSet<EmployeeDto> showSearchedEmployees(String term) {
	Connection _con;
	Statement _statement = null;
	_con = H2DataBaseUtils.getConnection();
	HashSet<EmployeeDto> _searchedEmployees = new HashSet<EmployeeDto>();

	if (_con != null) {

	    try {
		_statement = _con.createStatement();

		ResultSet rs = _statement.executeQuery("SELECT * FROM EMPLOYEE WHERE LOWER(FIRSTNAME) LIKE LOWER('%"
			+ term + "%') OR LOWER(LASTNAME) LIKE LOWER('%" + term + "%') ");

		while (rs.next()) {
		    _searchedEmployees.add(new EmployeeDto(rs.getInt("ID"), rs.getString("FIRSTNAME"),
			    rs.getString("LASTNAME"), rs.getString("PROJECT"), rs.getString("POSITION"), rs.getBoolean("ENABLED")));
		}
	    } catch (SQLException se) {
		System.out.println(se.getMessage());
	    } finally {
		if (_statement != null) {
		    try {
			_statement.close();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
		if (_con != null) {
		    try {
			_con.close();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
	    }
	}

	return _searchedEmployees;
    }

    public HashSet<Project> showSearchedProjects(String term) {
	Connection _con;
	Statement _statement = null;
	_con = H2DataBaseUtils.getConnection();
	HashSet<Project> _searchedProjects = new HashSet<Project>();

	if (_con != null) {

	    try {
		_statement = _con.createStatement();

		ResultSet rs = _statement
			.executeQuery("SELECT* FROM PROJECTS WHERE LOWER(PROJECT_NAME) LIKE LOWER('%" + term + "%')");

		while (rs.next()) {
		    _searchedProjects.add(new Project(rs.getInt("ID"), rs.getString("PROJECT_NAME"),
			    rs.getString("CLIENT_NAME"), rs.getDate("PROJECT_START_DATE"),
			    rs.getDate("PROJECT_END_DATE"), rs.getString("PROJECT_DESCRIPTION")));
		}
	    } catch (SQLException e) {
		e.printStackTrace();
	    } finally {
		if (_statement != null) {
		    try {
			_statement.close();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
		if (_con != null) {
		    try {
			_con.close();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
	    }
	}

	return _searchedProjects;
    }

    public boolean createJobPostTable() {
	boolean created = H2DataBaseUtils.createTable(
		"CREATE TABLE IF NOT EXISTS JOB_POSTS(ID INT PRIMARY KEY AUTO_INCREMENT,JOB_POSITION VARCHAR(255),"
			+ " PROJECT_NAME VARCHAR(255), JOB_TYPE VARCHAR(255), NO_OF_POSITIONS INT, CLOSING_DATE DATE,"
			+ " DESCRIPTION VARCHAR(255));");
	return created;
    }

    public HashSet<JobPost> showAllJobPosts() {
	Connection con;
	Statement statement = null;
	con = H2DataBaseUtils.getConnection();
	HashSet<JobPost> allJobPosts = new HashSet<JobPost>();

	if (con != null) {

	    try {
		statement = con.createStatement();

		ResultSet rs = statement.executeQuery("SELECT * FROM JOB_POSTS");

		while (rs.next()) {
		    allJobPosts.add(new JobPost(rs.getInt("ID"), rs.getString("JOB_POSITION"),
			    rs.getString("PROJECT_NAME"), rs.getString("JOB_TYPE"), rs.getInt("NO_OF_POSITIONS"),
			    rs.getDate("CLOSING_DATE"), rs.getString("DESCRIPTION")));
		}
	    } catch (SQLException se) {
		System.out.println(se.getMessage());
	    } finally {
		if (statement != null) {
		    try {
			statement.close();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
		if (con != null) {
		    try {
			con.close();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
	    }
	}

	return allJobPosts;
    }

    public void addNewJobPost(String jobPosition, String projectName, String jobType, int noOfPositions,
	    Date closingDate, String description) {
	Connection conn = null;
	PreparedStatement preparedStatement = null;
	String sqlQuery = "INSERT INTO JOB_POSTS(JOB_POSITION, PROJECT_NAME, JOB_TYPE, NO_OF_POSITIONS, CLOSING_DATE, DESCRIPTION) VALUES(?,?,?,?,?,?);";
	conn = H2DataBaseUtils.getConnection();

	if (conn != null) {
	    try {
		conn = H2DataBaseUtils.getConnection();
		preparedStatement = conn.prepareStatement(sqlQuery);

		preparedStatement.setString(1, jobPosition);
		preparedStatement.setString(2, projectName);
		preparedStatement.setString(3, jobType);
		preparedStatement.setInt(4, noOfPositions);
		java.sql.Date sqlDate = new java.sql.Date(closingDate.getTime());
		preparedStatement.setDate(5, sqlDate);
		preparedStatement.setString(6, description);		

		preparedStatement.executeUpdate();
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		if (preparedStatement != null) {
		    try {
			preparedStatement.close();
			if (conn != null) {
			    conn.close();
			}
		    } catch (Exception e2) {
			e2.printStackTrace();
		    }
		}
	    }
	}
    }

    public JobPost findJobPostById(int id) {
	Connection con;
	Statement statement = null;
	con = H2DataBaseUtils.getConnection();
	JobPost jobPost = null;

	if (con != null) {
	    try {
		statement = con.createStatement();

		ResultSet rs = statement.executeQuery("SELECT * FROM JOB_POSTS WHERE ID=" + id + ";");
		rs.next();

		jobPost = new JobPost(rs.getInt("ID"), rs.getString("JOB_POSITION"), rs.getString("PROJECT_NAME"),
			rs.getString("JOB_TYPE"), rs.getInt("NO_OF_POSITIONS"), rs.getDate("CLOSING_DATE"),
			rs.getString("DESCRIPTION"));

	    } catch (SQLException se) {
		System.out.println("Error in getUser: " + se.getMessage());
	    } finally {

		try {
		    statement.close();
		} catch (SQLException e) {
		    
		    e.printStackTrace();
		}

		try {
		    con.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	    }
	}
	return jobPost;
    }

    public HashSet<EmployeeDto> showPossibleEmployees() {

	HashSet<EmployeeDto> _possibleEmployees = new HashSet<EmployeeDto>();

	Connection _connection;
	Statement _statement = null;
	_connection = H2DataBaseUtils.getConnection();

	if (_connection != null) {

	    try {
		_statement = _connection.createStatement();

		ResultSet rs = _statement.executeQuery("SELECT* FROM EMPLOYEE WHERE PROJECT IS NULL AND ENABLED IS TRUE");

		while (rs.next()) {
			_possibleEmployees.add(new EmployeeDto(rs.getInt(1), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), null, null, true));
		}
	    } catch (SQLException e) {
		e.printStackTrace();
	    } finally {
		if (_statement != null) {
		    try {
			_statement.close();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
		if (_connection != null) {
		    try {
			_connection.close();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
	    }

	}
	return _possibleEmployees;
    }

    public void editPossibleEmployees(ArrayList<Integer> selectedEmployees, String projectName) {
	Connection _connection = H2DataBaseUtils.getConnection();

	Statement _statement = null;

	Iterator<Integer> iterator = selectedEmployees.iterator();
	
	if (_connection != null) {
	    try {
		while (iterator.hasNext()) {
		    int id = iterator.next();
		    System.out.println(projectName + "  aaaaaaa " + id);
		    String _sqlQuery = "UPDATE EMPLOYEE SET PROJECT = '" + projectName + "' WHERE ID = " + id;
		    _statement = _connection.createStatement();
		    _statement.executeUpdate(_sqlQuery);
		    
		}
	    } catch (SQLException e) {
		e.printStackTrace();
	    } finally {
		if (_statement != null) {
		    try {
			_statement.close();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
		if (_connection != null) {
		    try {
			_connection.close();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }

		}
	    }
	}
    }

    public boolean createTeamTable() {
	boolean _createTablesOrders = false;
	try {
	    H2DataBaseUtils.createTable(
		    "CREATE TABLE IF NOT EXISTS TEAM(PROJECT_NAME VARCHAR(255) REFERENCES PROJECTS(PROJECT_NAME), EMPLOYEE_ID INT REFERENCES EMPLOYEE(ID))");
	    _createTablesOrders = true;
	} catch (Exception e) {
	    e.printStackTrace();

	}

	return _createTablesOrders;
    }

    public void addToTeam(String projectName, int employeeID) {
	Connection _conn = H2DataBaseUtils.getConnection();
	PreparedStatement _preparedStatement = null;
	
	System.out.println(employeeID);
	
	String _sqlQuery = "INSERT INTO TEAM VALUES('" + projectName + "', " + employeeID +");";

	if (_conn != null) {
	    try {
		_conn = H2DataBaseUtils.getConnection();
		_preparedStatement = _conn.prepareStatement(_sqlQuery);

	//	_preparedStatement.setString(1, projectName);
	//	_preparedStatement.setInt(2, employeeID);

		_preparedStatement.executeUpdate();
	    } catch (Exception e) {
		e.printStackTrace();

	    } finally {
		if (_preparedStatement != null) {
		    try {
			_preparedStatement.close();
			if (_conn != null) {
			    _conn.close();
			}
		    } catch (Exception e2) {
			e2.printStackTrace();
		    }
		}
	    }
	}
    }

    public int findProject(String projectName) {
	int projectID = -1;
	Connection _connection;
	Statement _statement = null;
	_connection = H2DataBaseUtils.getConnection();

	if (_connection != null) {

	    try {
		_statement = _connection.createStatement();
		ResultSet rs = _statement
			.executeQuery("SELECT * FROM PROJECTS WHERE PROJECT_NAME LIKE '" + projectName + "'");

		if (rs.next()) {
		    projectID = rs.getInt("ID");
		}
	    } catch (SQLException e) {
		e.printStackTrace();
	    } finally {
		if (_statement != null) {
		    try {
			_statement.close();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
		if (_connection != null) {
		    try {
			_connection.close();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
	    }

	}

	return projectID;
    }

    public HashSet<JobPost> showSearchedJobPosts(String term){
    	Connection con;
    	Statement statement = null;
    	con = H2DataBaseUtils.getConnection();
    	HashSet<JobPost> searchedJobPosts = new HashSet<JobPost>();

    	if (con != null) {

    	    try {
    		statement = con.createStatement();

    		ResultSet rs = statement.executeQuery("SELECT * FROM JOB_POSTS WHERE LOWER(JOB_POSITION) LIKE LOWER('%" + term + "%')");

    		while (rs.next()) {
    		    searchedJobPosts.add(new JobPost(rs.getInt("ID"), rs.getString("JOB_POSITION"),
    			    rs.getString("PROJECT_NAME"), rs.getString("JOB_TYPE"), rs.getInt("NO_OF_POSITIONS"),
    			    rs.getDate("CLOSING_DATE"), rs.getString("DESCRIPTION")));
    		}
    	    } catch (SQLException se) {
    		System.out.println(se.getMessage());
    	    } finally {
    		if (statement != null) {
    		    try {
    			statement.close();
    		    } catch (SQLException e) {
    			e.printStackTrace();
    		    }
    		}
    		if (con != null) {
    		    try {
    			con.close();
    		    } catch (SQLException e) {
    			e.printStackTrace();
    		    }
    		}
    	    }
    	}

    	return searchedJobPosts;
    }
	
	
	public HashSet<JobCandidate> showSearchedJobCandidate(String term) {
	   	Connection _con;
	   	Statement _statement = null;
	   	_con = H2DataBaseUtils.getConnection();
	   	HashSet<JobCandidate> _searchedJobCandidate = new HashSet<JobCandidate>();

	   	if (_con != null) {

	   	    try {
	   		_statement = _con.createStatement();

	   		ResultSet rs = _statement.executeQuery("SELECT * FROM JOB_CANDIDATE WHERE LOWER(FIRSTNAME) LIKE LOWER('%"
	   			+ term + "%') OR LOWER(LASTNAME) LIKE LOWER('%" + term + "%') ");

	   		while (rs.next()) {
	   		 _searchedJobCandidate.add(new JobCandidate(rs.getInt("ID"), rs.getString("FIRSTNAME"),
	   			    rs.getString("LASTNAME"), rs.getString("EMAIL"), rs.getString("JOB_POSITION"), rs.getString("CV_ADDRESS")));
	   		}
	   	    } catch (SQLException se) {
	   		System.out.println(se.getMessage());
	   	    } finally {
	   		if (_statement != null) {
	   		    try {
	   			_statement.close();
	   		    } catch (SQLException e) {
	   			e.printStackTrace();
	   		    }
	   		}
	   		if (_con != null) {
	   		    try {
	   			_con.close();
	   		    } catch (SQLException e) {
	   			e.printStackTrace();
	   		    }
	   		}
	   	    }
	   	}

	   	return _searchedJobCandidate;
	       }

	public boolean createJobCandidatesTable() {
	    boolean created = H2DataBaseUtils.createTable(
			"CREATE TABLE IF NOT EXISTS JOB_CANDIDATE(ID INT PRIMARY KEY AUTO_INCREMENT,FIRSTNAME VARCHAR(255) NOT NULL,"
				+ "LASTNAME VARCHAR(255) NOT NULL,JOB_POSITION VARCHAR(255) NOT NULL, EMAIL VARCHAR(255) NOT NULL,"
				+ "PHONE VARCHAR(255) NOT NULL,"
				+ "EDUCATION VARCHAR(255) NOT NULL, WORKEXPIRIANCE VARCHAR(255) NOT NULL,"
				+ "HOBBIES VARCHAR(255), MOBILE VARCHAR(255), ADDRESS VARCHAR(255) NOT NULL,"
				+ "GENDER VARCHAR(30), CV_ADDRESS VARCHAR(255));");
		return created;
	}
	
	public boolean addNewJobCandidate(String firstName, String lastName, String gender,
		String address, String email, String phone, String mobile, String education, String workExpiriance,
		String hobbies, String position, String cvAddress) {

		boolean _employeeAdded = false;
		Connection _conn = H2DataBaseUtils.getConnection();
		PreparedStatement _preparedStatement = null;
		String _sqlQuery = "INSERT INTO JOB_CANDIDATE(FIRSTNAME, LASTNAME, JOB_POSITION, EMAIL, PHONE, EDUCATION, "
			+ "WORKEXPIRIANCE, HOBBIES, MOBILE, ADDRESS, GENDER, CV_ADDRESS) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";

		if (_conn != null) {
		    try {
			_conn = H2DataBaseUtils.getConnection();
			_preparedStatement = _conn.prepareStatement(_sqlQuery);

			_preparedStatement.setString(1, firstName);
			_preparedStatement.setString(2, lastName);			
			_preparedStatement.setString(3, position);
			_preparedStatement.setString(4, email);
			_preparedStatement.setString(5, phone);
			_preparedStatement.setString(6, education);
			_preparedStatement.setString(7, workExpiriance);
			_preparedStatement.setString(8, hobbies);
			_preparedStatement.setString(9, mobile);
			_preparedStatement.setString(10, address);
			_preparedStatement.setString(11, gender);
			_preparedStatement.setString(12, cvAddress);

			_preparedStatement.executeUpdate();
			_employeeAdded = true;
		    } catch (Exception e) {
			e.printStackTrace();

		    } finally {
			if (_preparedStatement != null) {
			    try {
				_preparedStatement.close();
				if (_conn != null) {
				    _conn.close();
				}
			    } catch (Exception e2) {
				e2.printStackTrace();
			    }
			}
		    }

		}

		return _employeeAdded;
	    }
	
	public boolean isEnabled(int id) {
		Connection _con;
		Statement _statement = null;
		_con = H2DataBaseUtils.getConnection();
		boolean enabled = false;

		if (_con != null) {

		    try {
			_statement = _con.createStatement();

			ResultSet rs = _statement
				.executeQuery("SELECT* FROM EMPLOYEE WHERE ID=" + id
					+ ";");

			rs.next();
			enabled = rs.getBoolean("ENABLED");

		    } catch (SQLException e) {
			e.printStackTrace();
		    } finally {
			if (_statement != null) {
			    try {
				_statement.close();
			    } catch (SQLException e) {
				e.printStackTrace();
			    }
			}
			if (_con != null) {
			    try {
				_con.close();
			    } catch (SQLException e) {
				e.printStackTrace();
			    }
			}
		    }
		}
		return enabled;

	    }

	    public void enableEmployee(int employeeId) {
		Connection _conn = H2DataBaseUtils.getConnection();
		PreparedStatement _preparedStatement = null;
		String _sqlQuery = "UPDATE EMPLOYEE SET ENABLED=TRUE WHERE ID="
			+ employeeId + ";";

		if (_conn != null) {
		    try {
			_conn = H2DataBaseUtils.getConnection();
			_preparedStatement = _conn.prepareStatement(_sqlQuery);
			_preparedStatement.executeUpdate();

		    } catch (Exception e) {
			e.printStackTrace();
		    } finally {
			if (_preparedStatement != null) {
			    try {
				_preparedStatement.close();
				if (_conn != null) {
				    _conn.close();
				}
			    } catch (Exception e2) {
				e2.printStackTrace();
			    }
			}
		    }
		}
	    }

	    public void disableEmployee(int employeeId) {
		Connection _conn = H2DataBaseUtils.getConnection();
		PreparedStatement _preparedStatement = null;
		String _sqlQuery = "UPDATE EMPLOYEE SET ENABLED=FALSE WHERE ID="
			+ employeeId + ";";
		if (_conn != null) {
		    try {
			_conn = H2DataBaseUtils.getConnection();
			_preparedStatement = _conn.prepareStatement(_sqlQuery);
			_preparedStatement.executeUpdate();

		    } catch (Exception e) {
			e.printStackTrace();
		    } finally {
			if (_preparedStatement != null) {
			    try {
				_preparedStatement.close();
				if (_conn != null) {
				    _conn.close();
				}
			    } catch (Exception e2) {
				e2.printStackTrace();
			    }
			}
		    }
		}
	    }

	    public EmployeeDto getEmployee(int id) {
		// TODO Auto-generated method stub
		return null;
	    }

}