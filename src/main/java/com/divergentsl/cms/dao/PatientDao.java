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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.divergentsl.cms.database.IDatabaseManager;
import com.divergentsl.cms.dto.PatientDto;


/**
 * 
 * It is a helper class for performing CRUD operation on patient
 *
 */
@Repository("patientDao")
public class PatientDao {
	
	@Autowired
	IDatabaseManager databaseManager;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public static final String ID = "patient_id";
	public static final String NAME = "patient_name";
	public static final String AGE = "age";
	public static final String WEIGHT = "weight";
	public static final String GENDER = "gender";
	public static final String CONTACT_NUMBER = "contact_number";
	public static final String ADDRESS = "address";
	
	public PatientDao(IDatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}
	
	/**
	 * It is a helper method for inserting new patient data.
	 * @param inputData
	 * @return 1 if data inserted successfully, otherwise it returns false.
	 * @throws SQLException
	 */
	public int insert(PatientDto data) {

		String query = "insert into patient (?, ?, ?, ?, ?, ?) values ('?', ?, ?, '?', ?, '?')";
		int i = jdbcTemplate.update(query, NAME, AGE, WEIGHT, GENDER, CONTACT_NUMBER, ADDRESS, data.getPatientName(), data.getAge(), data.getWeight(), data.getGender(), data.getContactNumber(), data.getAddress());
		
		return i;
	}
	
	
	/**
	 * It is a helper method for search patient data
	 * @param patientId
	 * @return Map of patient data if patient is found, otherwise it returns empty Map.
	 * @throws SQLException
	 */
	public PatientDto search(String patientId) throws SQLException {
		
		String query = "select * from patient where patient_id = ?";
		
		RowMapper<PatientDto> rowMapper = new RowMapperPatient();
		
		PatientDto patient = jdbcTemplate.queryForObject(query, rowMapper, patientId);
		
		return patient;
	}
	
	
	/**
	 * It is a helper method for retriving all patient data.
	 * @return List of Map of patient data.
	 * @throws SQLException
	 */
	public List<Map<String, String>> listAll() throws SQLException {
		
		Connection con = null;
		Statement st = null;
		
		con = databaseManager.getConnection();
		st = con.createStatement();
		
		ResultSet rs = st.executeQuery("select * from patient");
		List<Map<String, String>> list = new ArrayList<>();
		
		while(rs.next()) {
			Map<String, String> record = new HashMap<>();
			record.put("id", rs.getString(1));
			record.put("name", rs.getString(2));
			record.put("age", rs.getString(3));
			record.put("weight", rs.getString(4));
			record.put("gender", rs.getString(5));
			record.put("contactNumber", rs.getString(6));
			record.put("address", rs.getString(7));
			list.add(record);
		}
		st.close();
		con.close();
		return list;
	}
	
	
	/**
	 * It is a helper method for delete a specific patient data.
	 * @param patientId
	 * @return 1 if data deleted succesfully, otherwise it returns false.
	 * @throws SQLException
	 */
	public int delete(String patientId) {
		
		String query = "delete from patient where ? = ?";
		
		int i = jdbcTemplate.update(query, ID, patientId);
		return i;
	}
	
	
	/**
	 * It is a helper method for update the patient data.
	 * @param data
	 * @return 1 if data successfully updated, otherwise it returns 0.
	 * @throws SQLException
	 */
	public int update(PatientDto data) {
	
		String query = "update patient set ? = '?', ? = ?, ? = ?, ? = '?', ? = '?', ? = '?' where ? = ?";
		
		int i = jdbcTemplate.update(query, NAME, data.getPatientName(), AGE, data.getAge(), WEIGHT, data.getWeight(), GENDER, data.getGender(), CONTACT_NUMBER, data.getContactNumber(), ADDRESS, data.getAddress(), ID, data.getId());
		
		return i;
	}

}
