package com.divergentsl.cms;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.divergentsl.cms.config.JavaConfiguration;

/**
 * It is a main class where application execution start
 * @author Aditya Meena
 *
 */
public class ClinicManagementSystem {

	private static Logger logger = LoggerFactory.getLogger(Login.class);
	
    public static void main(String args[]) {

        
    	@SuppressWarnings("resource")
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    	context.getEnvironment().setActiveProfiles("dev");
    	context.register(JavaConfiguration.class);
    	context.refresh();
    	Login login = context.getBean("login", Login.class);
    	
    	logger.info("Properties:{}", login.getPropertyValue());
    	
    	login.loginPanel();
    	
    }

}
