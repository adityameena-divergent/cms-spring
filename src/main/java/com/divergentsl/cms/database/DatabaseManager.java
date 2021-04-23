package com.divergentsl.cms.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DatabaseManager implements IDatabaseManager{
	
	private static Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
	
	
	
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.debug(e.getMessage());
			System.exit(100);
		}
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(IDatabaseManager.url, IDatabaseManager.username, IDatabaseManager.password);
	}
}
