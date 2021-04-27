package com.divergentsl.cms.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan(basePackages = {"com.divergentsl.cms","com.divergentsl.cms.database","com.divergentsl.cms.dao", "com.divergentsl.cms.aspect"})
@Profile("dev")
@PropertySource("classpath:/application.properties")
@EnableAspectJAutoProxy
public class JavaConfiguration {

	@Value("${spring.datasource.url}")
	public String databaseURL;
	@Value("${spring.datasource.username}")
	public String databaseUsername;
	@Value("${spring.datasource.password}")
	public String databasePassword;
	
	 @Bean
	    public DataSource mysqlDataSource() {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	        dataSource.setUrl(databaseURL);
	        dataSource.setUsername(databaseUsername);
	        dataSource.setPassword(databasePassword);
	        return dataSource;
	    }
	 
	 @Bean
	 public JdbcTemplate jdbcTemplate() {
		 return new JdbcTemplate(mysqlDataSource());
	 }

	
}
