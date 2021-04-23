package com.divergentsl.cms;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;



/**
 * This Login class contains methods for login admin and doctor
 */

@Component("login")
public class Login {

	private static Logger logger = LoggerFactory.getLogger(Login.class);
	
	
	@Autowired
	private Admin admin;
	@Autowired
	private Doctor doctor;
	
	@Autowired
	private Environment env;
		
	

    /**
     * This method is loginPanel which ask for that you want to login as admin or doctor
     * for admin or doctor to login, first admin have to login if username & password is correct then it will redirect admin to adminPanel if it login as admin or if it will login as doctor then it will redirect ot doctorPanel, other wise it will print message "You are not authorised"
     */
    public void loginPanel() {

    	System.setProperty("slf4j.detectLoggerNameMismatch", "true");
    	
    	

        Main:
        while(true) {

            this.printLoginOption();

            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            Login:
            switch (input) {

                case "1":
                    if (admin.adminLogin()) {
                        admin.adminPanel();
                    } else {
                    	logger.info("You are not authorised!");
                    }
                    break;

                case "2":

                    String did = doctor.doctorLogin();

                    if (did != null) {
                        String s[] = did.split(" ");
                        doctor.doctorPanel(s[0], s[1]);
                    } else {
                    	logger.info("You are not Authorised");
                        break Login;
                    }
                    break;

                case "3":
                    break Main;

                default:
                	logger.info("Invalid Input!");
                    break;
            }
        }
    }
    
    private void printLoginOption() {
    	System.out.println("\n----Login Panel----");
        System.out.println("1. Admin");
        System.out.println("2. Doctor");
        System.out.println("3. Exit");
    }
    
    public String getPropertyValue() {
    	return env.getProperty("props");
    }


}
