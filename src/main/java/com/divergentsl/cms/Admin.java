package com.divergentsl.cms;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.divergentsl.cms.dao.LoginDao;

@Component("admin")
public class Admin {

	private static Logger logger = LoggerFactory.getLogger(Admin.class);
	
	@Autowired
	private LoginDao loginDao;
	@Autowired
	private CRUDDoctor crudDoctor;
	@Autowired
	private CRUDDrugs crudDrug;
	@Autowired
	private CRUDPatient crudPatient;
	@Autowired
	private PatientAppointment patientAppointment;
	@Autowired
	private LabTest labTest;
	
	
	

    /**
     * This method takes two input username and password from console.
     * @return It return true if username and password is correct otherwise return false.
     */
    public boolean adminLogin() {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n-----Admin Login------");
        System.out.print("\nEnter Username: ");
        String username = sc.nextLine();

        System.out.print("\nEnter Password: ");
        String password = sc.nextLine();

        try {

            if(loginDao.adminLogin(username, password)) {
            	logger.info("Login Successful...");
                return true;
            } else {
            	logger.info("Incorrect Username & Password!");
                return false;
            }

        } catch(Exception e) {
        	logger.info(e.getMessage());
        }
        return false;
    }


    /**
     * This method print all the operation that admin can perform, and ask for input for which operation admin want to perform, then after it redirect to that specific operation panel according to input.
     */
    public void adminPanel() {
    	
    	
        LOGOUT:
        while(true) {

            this.printAdminOptions();
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            switch (input) {

                case "1":
                    crudPatient.CRUDOperation();
                    break;

                case "2":
                    crudDoctor.CRUDOperation();
                    break;

                case "3":
                    crudDrug.CRUDOperations();
                    break;

                case "4":
                    labTest.labTestPanel();
                    break;

                case "5":
                    patientAppointment.makeAppointment();
                    break;

                case "6":
                    break LOGOUT;

                default:
                	logger.info("Invalid Input!");
                    break;
            }
        }
    }


    /**
     * This method print all the operations that admin can perform
     */
    public void printAdminOptions() {

        System.out.println("\n----Admin Panel-----");
        System.out.println("1. Patient");
        System.out.println("2. Doctor");
        System.out.println("3. Drug");
        System.out.println("4. Lab Test");
        System.out.println("5. Make appointment");
        System.out.println("6. Logout");
        System.out.print("Enter Your Choice: ");

    }






}
