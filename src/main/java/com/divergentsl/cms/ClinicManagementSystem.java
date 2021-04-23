package com.divergentsl.cms;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.divergentsl.cms.config.JavaConfiguration;

/**
 * It is a main class where application execution start
 * @author Aditya Meena
 *
 */
public class ClinicManagementSystem {

    public static void main(String args[]) {

        
    	@SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfiguration.class);
    	
    	Login login = context.getBean("login", Login.class);
    	login.loginPanel();
    	
    }

}
