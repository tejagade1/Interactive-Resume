package com.teja.dao;

import java.sql.Connection;

import javax.naming.*;
import javax.sql.*;

/**
 * This class returns the Oracle database connect object from
 * a Oracle Express Virtual Machine
 * 
 * @author Teja Gade
 *
 */
public class DBConnection {

	private static DataSource dataSource = null; //hold the database object
	private static Context context = null; //used to lookup the database connection in weblogic
	
	/**
	 * This is a public method that will return the database connection.
	 * 
	 * @return Database object
	 * @throws Exception
	 */
	private static DataSource dataSourceConn() throws Exception {
		
		/**
		 * check to see if the database object is already defined...
		 * if it is, then return the connection, no need to look it up again.
		 */
		if (null != dataSource) {
			return dataSource;
		}
		
		try {
			
			if (null == context) {
				context = new InitialContext();
			}
			
			dataSource = (DataSource) context.lookup("oracledb");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return dataSource;
		
	}
	
	/**
	 * This method will return the connection to the Oracle schema
	 * 
	 * @return Connection to Oracle database.
	 */
	protected static Connection getOracleDBConnection() {
		Connection conn = null;
		try {
			conn = dataSourceConn().getConnection();
			return conn;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}

