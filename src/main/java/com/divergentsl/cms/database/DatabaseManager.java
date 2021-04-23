package com.divergentsl.cms.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DatabaseManager implements IDatabaseManager{
	
	private static Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
	
	@Autowired
	Environment env;
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.debug(e.getMessage());
			System.exit(100);
		}
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(env.getProperty(URL), env.getProperty(USERNAME), env.getProperty(PASSWORD));
	}
}
