package com.divergentsl.cms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.divergentsl.cms.database.IDatabaseManager;



/**
 * This class is a helper class for performing CRUD operation on drug
 */
@Repository("drugDao")
public class DrugDao {
	
	@Autowired
	IDatabaseManager databaseManager;
	
	public final String ID = "drug_id";
	public final String DESCRIPTION = "description";
	public final String NAME = "drug_name";

	@Autowired
	JdbcTemplate jdbcTemplate;

	
	public DrugDao(IDatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}

	/**
	 * It is a helper method for adding a new drug
	 * @param name
	 * @param description
	 * @return 1 if drug added successfully, otherwise it return 0.
	 * @throws SQLException
	 */
	public int add(String name, String description) throws SQLException {
		
		String query = "insert into drug (?, ?) values ('?','?')";
		
		int i = jdbcTemplate.update(query, NAME, DESCRIPTION, description);		
		
		return i;
	}

	
	/**
	 * It is a helper method for search the drug by drug id
	 * @param id
	 * @return Map of drug data if drug found, otherwise returns empty Map.
	 * @throws SQLException
	 */
	public Map<String, String> search(String id) throws SQLException {
    	
    	Connection con = null;
    	Statement st = null;
    	
    	con = databaseManager.getConnection();
    	st = con.createStatement();
    	
    	ResultSet rs = st.executeQuery("select * from drug where " + ID + " = " + id);
    	Map<String, String> data = new HashMap<>();
    	if (rs.next()) {
    		data.put("id", rs.getString(1));
    		data.put("name", rs.getString(2));
    		data.put("description", rs.getString(3));
    	}
		st.close();
		con.close();
    	return data;
    }
	

	/**
	 * It is helper method for deleting the drug data by  drug id.
	 * @param id
	 * @return 1 if drug data successfully deleted, otherwise it returns 0.
	 * @throws SQLException
	 */
	public int delete(String id) {
		
		String query = "delete from drug where ? = ?";
		int i = jdbcTemplate.update(query, ID, id);

		return i;
	}
	
	
	/**
	 * It is helper method for update the specific drug data
	 * @param data
	 * @return 1 if drug data successfully updated, otherwise it returns 0.
	 * @throws SQLException
	 */
	public int update(Map<String, String> data) throws SQLException {
		
		String query = "update drug set ? = '?', ? = '?' where ? = ?";
		
		int i = jdbcTemplate.update(query, NAME, data.get("name"), DESCRIPTION, data.get("description"), ID, data.get("id"));
		
		return i;
	}

}
