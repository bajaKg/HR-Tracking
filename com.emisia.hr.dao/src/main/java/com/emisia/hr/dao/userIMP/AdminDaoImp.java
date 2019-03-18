package com.emisia.hr.dao.userIMP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import com.emisia.hr.dao.H2DataBaseUtils;
import com.emisia.hr.dao.user.AdminDao;
import com.emisia.hr.model.User;

public class AdminDaoImp implements AdminDao {

    static {
	H2DataBaseUtils.createTables();
    }

    public void editUser(int id, String password, String role) {
	Connection _connection = H2DataBaseUtils.getConnection();

	Statement _statement = null;
	String _sqlQuery = "UPDATE USER SET PASSWORD = '" + password + "' WHERE ID = " + id;

	if (_connection != null) {
	    try {
		_statement = _connection.createStatement();
		_statement.executeUpdate(_sqlQuery);
		_sqlQuery = " UPDATE USER SET ROLE = '" + role + "' WHERE ID = " + id;
		_statement.executeUpdate(_sqlQuery);
		_statement.close();
		_connection.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }

    public void editEmployeExit(int id, String reasonLeaving) {
	String _sqlQuery = "UPDATE EMPLOYEE_EXIT SET REASON_LEAVING = '" + reasonLeaving + "' WHERE ID = " + id;

	HRDaoImp.ExecuteYourQuery(_sqlQuery);
    }

    public static synchronized boolean addUserTableTest() {
	String _SQLquery = "INSERT INTO USER(FIRSTNAME, LASTNAME, USERNAME, PASSWORD, EMAIL, ROLE, ENABLED) VALUES('Milos','Rakonjac','mrakonjac','mrr','milos@live.com','admin', true);"
		+ "INSERT INTO USER(FIRSTNAME, LASTNAME, USERNAME, PASSWORD, EMAIL, ROLE, ENABLED) VALUES('Milos','Bajic','mbajic','mbb','baja@live.com','hr', false);"
		+ "INSERT INTO USER(FIRSTNAME, LASTNAME, USERNAME, PASSWORD, EMAIL, ROLE, ENABLED) VALUES('Katarina','Ralevic','kralevic','krr','kata@live.com','admin',true);"
		+ "INSERT INTO USER(FIRSTNAME, LASTNAME, USERNAME, PASSWORD, EMAIL, ROLE, ENABLED) VALUES('Djordje','Jovanovic','djovanovic','djj','djole@live.com','hr',false);"
		+ "INSERT INTO USER(FIRSTNAME, LASTNAME, USERNAME, PASSWORD, EMAIL, ROLE,ENABLED) VALUES('Admin','Admin','admin','admin','admin@live.com','admin', true);"
		+ "INSERT INTO USER(FIRSTNAME, LASTNAME, USERNAME, PASSWORD, EMAIL, ROLE, ENABLED) VALUES('Milos','Rakonjac','mrakonjac','mil90','losmi@gmail.com','admin', true);";
	return HRDaoImp.insertTable(_SQLquery);
    }

    public static synchronized boolean addUserEmployeeExitTable() {
	String _SQLquery = "INSERT INTO EMPLOYEE_EXIT(FIRSTNAME, LASTNAME, TIME_OF_DISABLING, REASON_LEAVING)"
		+ " VALUES('Osoba0','Prezime0',NULL,'MNOGO JE LENJ');"
		+ "INSERT INTO EMPLOYEE_EXIT(FIRSTNAME, LASTNAME, TIME_OF_DISABLING, REASON_LEAVING) VALUES('Osoba1','Preizme1',NULL, 'FILOZOFIRA, imao je vise pogresnih procena. Tesko se adaptira na nove radne i promenu yadataka');"
		+ "INSERT INTO EMPLOYEE_EXIT(FIRSTNAME, LASTNAME, TIME_OF_DISABLING, REASON_LEAVING) VALUES('Osoba2','Preizme2',NULL, 'TEHNOLOSKI VISAK');"
		+ "INSERT INTO EMPLOYEE_EXIT(FIRSTNAME, LASTNAME, TIME_OF_DISABLING, REASON_LEAVING) VALUES('Osoba3','Preizme3',NULL,'KASNII MNOGOO');";
	return HRDaoImp.insertTable(_SQLquery);
    }

    public boolean addUser(String firstName, String lastName, String userName, String password, String email,
	    String role, boolean enabled) throws SQLException {
	Connection _conn = null;
	PreparedStatement _preparedStatement = null;
	boolean _addUser = false;
	String _sqlQuery = "INSERT INTO USER(FIRSTNAME, LASTNAME, USERNAME, PASSWORD, EMAIL, ROLE, ENABLED) VALUES(?,?,?,?,?,?,?);";
	_conn = H2DataBaseUtils.getConnection();

	if (_conn != null) {
	    try {
		_conn = H2DataBaseUtils.getConnection();
		_preparedStatement = _conn.prepareStatement(_sqlQuery);

		_preparedStatement.setString(1, firstName);
		_preparedStatement.setString(2, lastName);
		_preparedStatement.setString(3, userName);
		_preparedStatement.setString(4, password);
		_preparedStatement.setString(5, email);
		_preparedStatement.setString(6, role);
		_preparedStatement.setBoolean(7, enabled);

		if (findUserInTable(userName) == true) {
		    System.out.println("Username vec postoji");
		    _addUser = false;

		} else {
		    _preparedStatement.executeUpdate();

		    _addUser = true;
		}
	    } catch (Exception e) {
		System.out.println("addUser: " + e.getMessage());

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

	return _addUser;
    }

    public static boolean findUserInTable(String userName) throws SQLException {
	Connection _conn = null;
	PreparedStatement _preparedStatement = null;
	boolean _findUser = false;
	String _sqlQuery = "SELECT* FROM USER";
	_conn = H2DataBaseUtils.getConnection();
	if (_conn != null) {
	    try {
		_preparedStatement = _conn.prepareStatement(_sqlQuery);

		ResultSet rs = _preparedStatement.executeQuery();
		while (rs.next()) {

		    String _userName = rs.getString("USERNAME");
		    if (_userName.equals(userName) == true) {
			_findUser = true;
			break;

		    } else
			_findUser = false;
		}
	    } catch (SQLException se) {
		System.out.println("findUserInTable: " + se.getMessage());
	    } finally {
		if (_preparedStatement != null) {
		    _preparedStatement.close();
		}
		if (_conn != null) {
		    _conn.close();
		}
	    }
	}

	return _findUser;
    }

    public static boolean loginFormFindUsernamePassword(String userName, String password) throws SQLException {
	Connection _conn = null;
	PreparedStatement _preparedStatement = null;
	boolean _findUser = false;
	String _sqlQuery = "SELECT* FROM USER";
	_conn = H2DataBaseUtils.getConnection();
	if (_conn != null) {
	    try {
		_preparedStatement = _conn.prepareStatement(_sqlQuery);

		ResultSet rs = _preparedStatement.executeQuery();
		while (rs.next()) {

		    String _userName = rs.getString("USERNAME");
		    String _password = rs.getString("PASSWORD");

		    if (_userName.equals(userName) && _password.equals(password)) {
			_findUser = true;
			break;
		    } else {

			_findUser = false;

		    }

		}
	    } catch (SQLException se) {
		System.out.println("loginFormFindUsernamePassword: " + se.getMessage());
	    } finally {
		if (_preparedStatement != null) {
		    _preparedStatement.close();
		}
		if (_conn != null) {
		    _conn.close();
		}
	    }
	}
	return _findUser;
    }

    public String findRole(String userName, String password) throws SQLException {
	Connection _conn = null;
	PreparedStatement _preparedStatement = null;
	String _role = null;
	String _sqlQuery = "SELECT* FROM USER WHERE USERNAME LIKE '" + userName + "' AND PASSWORD LIKE'" + password
		+ "' ";
	_conn = H2DataBaseUtils.getConnection();
	if (_conn != null) {
	    try {
		_preparedStatement = _conn.prepareStatement(_sqlQuery);

		ResultSet rs = _preparedStatement.executeQuery();
		while (rs.next()) {

		    _role = rs.getString("ROLE");
		}

	    } catch (SQLException se) {
		System.out.println("findRole: " + se.getMessage());
	    } finally {
		if (_preparedStatement != null) {
		    _preparedStatement.close();
		}
		if (_conn != null) {
		    _conn.close();
		}
	    }
	}

	return _role;
    }

    public HashSet<User> showAllUsers() {
	Connection _con;
	Statement _statement = null;
	_con = H2DataBaseUtils.getConnection();
	HashSet<User> _allUsers = new HashSet<User>();

	if (_con != null) {

	    try {
		_statement = _con.createStatement();

		ResultSet rs = _statement.executeQuery("SELECT* FROM USER");

		while (rs.next()) {
		    _allUsers.add(new User(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
			    rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getString("EMAIL"),
			    rs.getString("ROLE"), rs.getBoolean("ENABLED")));

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

	return _allUsers;
    }

    public void enableUser(int userId) {
	Connection _connection = H2DataBaseUtils.getConnection();
	Statement _statement = null;
	String _sqlQuery = "UPDATE USER SET ENABLED = true WHERE ID = " + userId;

	if (_connection != null) {
	    try {
		_statement = _connection.createStatement();
		_statement.executeUpdate(_sqlQuery);
		_statement.close();
		_connection.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }

    public void disableUser(int userId) {
	Connection _connection = H2DataBaseUtils.getConnection();
	Statement _statement = null;
	String _sqlQuery = "UPDATE USER SET ENABLED = false WHERE ID = " + userId;

	if (_connection != null) {
	    try {
		_statement = _connection.createStatement();
		_statement.executeUpdate(_sqlQuery);
		_statement.close();
		_connection.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }

    public User getUser(int i) {
	Connection _con;
	Statement _statement = null;
	_con = H2DataBaseUtils.getConnection();
	User user = null;

	if (_con != null) {
	    try {
		_statement = _con.createStatement();

		ResultSet rs = _statement.executeQuery("SELECT * FROM USER WHERE ID=" + i + ";");
		rs.next();

		user = new User(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"),
			rs.getString("userName"), rs.getString("password"), rs.getString("email"), rs.getString("role"),
			rs.getBoolean("enabled"));

	    } catch (SQLException se) {
		System.out.println("Error in getUser: " + se.getMessage());
	    } finally {

		try {
		    _statement.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}

		try {
		    _con.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	    }
	}
	return user;
    }

    public HashSet<User> showSearchedUsers(String term) {
	Connection _con;
	Statement _statement = null;
	_con = H2DataBaseUtils.getConnection();
	HashSet<User> _searchedUsers = new HashSet<User>();

	if (_con != null) {

	    try {
		_statement = _con.createStatement();

		ResultSet rs = _statement.executeQuery("SELECT* FROM USER WHERE LOWER(USERNAME) LIKE LOWER('%" + term
			+ "%') OR LOWER(FIRSTNAME) LIKE LOWER('%" + term + "%') ");

		while (rs.next()) {
		    _searchedUsers.add(new User(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
			    rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getString("EMAIL"),
			    rs.getString("ROLE"), rs.getBoolean("ENABLED")));

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

	return _searchedUsers;
    }

}
