package com.divergentsl.cms.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabaseManager {

	
	String USERNAME = "spring.datasource.username";
	
	String PASSWORD = "spring.datasource.password";
	
	String URL = "spring.datasource.url";
	public Connection getConnection() throws SQLException ;

}
