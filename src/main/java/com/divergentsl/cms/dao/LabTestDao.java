package com.divergentsl.cms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.divergentsl.cms.database.IDatabaseManager;


/**
 * It is a helper class for performing CRUD operation on lab.
 *
 */
@Repository("labTestDao")
public class LabTestDao {
	
	@Autowired
	IDatabaseManager databaseManager;
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public LabTestDao(IDatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}
	
	public final String TEST_NAME = "test_name";
	public final String PATIENT_ID = "patient_id";
	public final String TEST_FEE = "test_fee";
	public final String TEST_ID = "test_id";
	
	
	/**
	 * It is a helper method for add new lab test data
	 * @param patientId
	 * @param testName
	 * @param fee
	 * @return 1 if test data successfully add, otherwise it returns 0.
	 * @throws SQLException
	 */
	public int add(String patientId, String testName, String fee) {

		String query = "insert into lab_test (?, ?, ?) values ('?', ?, ?)";
		
		int i = jdbcTemplate.update(query, TEST_NAME, PATIENT_ID, TEST_FEE, testName, patientId, fee);
		
		return i;
	}
	
	
	/**
	 * It is a helper for search lab test data
	 * @param testId
	 * @return Map of lab test data if data is found otherwise return empty Map.
	 * @throws SQLException
	 */
	public Map<String, String> search(String testId) throws SQLException {

		Connection con = null;
		Statement st = null;

		con = databaseManager.getConnection();
		st = con.createStatement();

		ResultSet rs = st.executeQuery("select * from lab_test where " + TEST_ID + " = " + testId);
		Map<String, String> data = new HashMap<>();

		if (rs.next()) {
			data.put("testId", rs.getString(1));
			data.put("testName", rs.getString(2));
			data.put("patientId", rs.getString(3));
			data.put("fee", rs.getString(4));
		}

		st.close();
		con.close();
		return data;
	}
	
	
	/**
	 * It is helper method for listing out all the lab test present in database.
	 * @return List of Map.
	 * @throws SQLException
	 */
	public List<Map<String, String>> listTest() throws SQLException {

		Connection con = null;
		Statement st = null;

		con = databaseManager.getConnection();
		st = con.createStatement();

		ResultSet rs = st.executeQuery(
				"select " + TEST_ID + ", " + TEST_NAME + ", p.patient_id, p.patient_name, p.contact_number, " + TEST_FEE + " from lab_test l join patient p on p.patient_id = l.patient_id");
		List<Map<String, String>> list = new ArrayList<>();

		while (rs.next()) {

			Map<String, String> data = new HashMap<>();
			data.put("testId", rs.getString(1));
			data.put("testName", rs.getString(2));
			data.put("patientId", rs.getString(3));
			data.put("patientName", rs.getString(4));
			data.put("contactNumber", rs.getString(5));
			data.put("fee", rs.getString(6));

			list.add(data);
		}

		st.close();
		con.close();
		return list;
	}
	
	
	/**
	 * It is a helper method for delete test data.
	 * @param testId
	 * @return 1 if data successfully delete, otherwise it returns 0.
	 * @throws SQLException
	 */
	public int delete(String testId) {
		
		String query = "delete from lab_test where ? = ?";
		
		int i = jdbcTemplate.update(query, TEST_ID, testId);
		
		return i;
	}
	
	
	/**
	 * It is a helper for updating the lab test data.
	 * @param data
	 * @return 1 if data successfully updated, otherwise return 0.
	 * @throws SQLException
	 */
	public int update(Map<String, String> data) throws SQLException {
		
		String query = "update lab_test set ? = '?', ? = ?, ? = ? where ? = ?";
		
		int i = jdbcTemplate.update(query, TEST_NAME, data.get("testName"), TEST_ID, data.get("patientId"), TEST_FEE, data.get("fee"), TEST_ID, data.get("testId"));
		
		return i;
	}

}
