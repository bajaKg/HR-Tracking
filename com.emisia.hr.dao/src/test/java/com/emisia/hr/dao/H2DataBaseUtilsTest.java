package com.emisia.hr.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

public class H2DataBaseUtilsTest {

	@Test
	public void getConnection() throws SQLException
	{
		Connection _conn = H2DataBaseUtils.getConnection();
		/*if(conn == null)
		try 
		{
			conn.close();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		}*/
		
		assertNotNull(_conn);

		assertFalse(_conn.isClosed());
	}
	
	@Test
	public void createTables()
	{
		assertTrue(H2DataBaseUtils.createTables());
	}
	
	@Test
	public void printRecords() throws SQLException
	{
		H2DataBaseUtils.printRecords();
	}

	
	
	
	
}