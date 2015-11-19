package com.teja.dao;

import java.sql.*;

import org.codehaus.jettison.json.JSONArray;

import com.teja.util.ToJSON;

/**
 * This java class will hold all the sql queries
 * 
 * @author Teja Gade
 */
public class DatabaseSchema extends DBConnection {

	/**
	 * This method allows you to update PERSON_SKILL_SET table
	 * 
	 * @param pk
	 * @param avail
	 * @return
	 * @throws Exception
	 */
	public int updateSkillSet(int pk, String skillName, int profLvl) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		
		try {
			/*
			 * TODO: 1. Add logic to validate the input parameters
			 * 2. Add logic to check table if the row exists
			 */
			
			conn = getOracleDBConnection();
			query = conn.prepareStatement("update PERSON_SKILL_SET " +
											"set PROFICIENCY_LEVEL = ? " +
											"where PERSON_ID = ? " + 
											" and SKILL_NAME = ?");
			
			query.setInt(1, profLvl);
			query.setInt(2, pk);
			query.setString(3, skillName);
			query.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
			return 500;
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return 200;
	}
	
	/**
	 * This method will insert a record into the PERSON_SKILL_SET table. 
	 * 
	 * @param SPECIFICATION
	 * @param SKILL_NAME
	 * @param PROFICIENCY_LEVEL
	 * @return integer 200 for success, 500 for error
	 * @throws Exception
	 */
	public int insertSkillSet(String SPECIFICATION, 
							  String SKILL_NAME, 
							  String PROFICIENCY_LEVEL) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		try {
			/*
			 *TODO: 1. Add logic to validate the input parameters
			 * 2. Add logic to check table if the row exists
			 */
			conn = getOracleDBConnection();
			query = conn.prepareStatement("insert into PERSON_SKILL_SET " +
					"(SPECIFICATION, SKILL_NAME, PROFICIENCY_LEVEL, PERSON_ID) " +
					"VALUES ( ?, ?, ?, ? ) ");

			query.setString(1, SPECIFICATION);
			query.setString(2, SKILL_NAME);
			
			int profLvlInt = Integer.parseInt(PROFICIENCY_LEVEL);
			query.setInt(3, profLvlInt);
			/*
			 * Setting the default person id to 12345. Future Enhancement includes the support for
			 * multiple persons. 
			 */
			
			query.setInt(4, 12345);

			query.executeUpdate(); 

		} catch(Exception e) {
			e.printStackTrace();
			return 500; //if a error occurs, return a 500
		}
		finally {
			if (conn != null) conn.close();
		}

		return 200;
	}
	
	/**
	 * This method will return all the Skills for a person.
	 * 
	 * @return - all Skills in json format
	 * @throws Exception
	 */
	public JSONArray getSkillSets() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try {
			conn = getOracleDBConnection();
			query = conn.prepareStatement("select PERSON_SKILL_SET_ID, SPECIFICATION, SKILL_NAME, PROFICIENCY_LEVEL " +
											"from PERSON_SKILL_SET");
			
			ResultSet rs = query.executeQuery();
			
			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return json;
	}

}
