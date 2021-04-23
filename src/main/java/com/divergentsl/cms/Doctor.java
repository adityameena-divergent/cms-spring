package com.divergentsl.cms;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.divergentsl.cms.dao.LoginDao;

@Component("doctor")
public class Doctor {
	
	private static Logger logger = LoggerFactory.getLogger(Doctor.class);
	
	@Autowired
	private LoginDao loginDao;
	@Autowired
	private PatientAppointment patientAppointment;
	


    private void printDoctorOptions(String dname) {

        System.out.println("\n----Login as : " + dname + "----");
        System.out.println("----Doctor Panel----");
        System.out.println("1. List of patients");
        System.out.println("2. Add prescription and notes for a patient");
        System.out.println("3. See booked appointments for him");
        System.out.println("4. Check patient history and his prescription");
        System.out.println("5. Create Invoice of patient");
        System.out.println("6. Logout");
        System.out.print("Enter Your Choice: ");

    }



    public void doctorPanel(String doctorId, String doctorName) {

        Logout:
        while(true) {

            printDoctorOptions(doctorName);

            Scanner sc = new Scanner(System.in);

            String input = sc.nextLine();

            switch (input) {

                case "1":
                	patientAppointment.allPatientList(doctorName);
                    break;

                case "2":
                	patientAppointment.addPrescription();
                    break;

                case "3":
                	patientAppointment.patientAppointToYou(doctorId, doctorName);
                    break;

                case "4":
                	patientAppointment.checkPatientHistory();
                    break;

                case "5":
                	patientAppointment.generateInvoice();
                    break;

                case "6":
                    break Logout;
                
                default:
                	logger.info("Invalid Input!");
                	break;
            }
        }
    }


    public String doctorLogin() {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n----Doctor Login----");
        System.out.print("Enter Username: ");
        String username = sc.nextLine();
        System.out.print("\nEnter Password: ");
        String password = sc.nextLine();

        try {
            
        	
        	
        	String doctorName = loginDao.doctorLogin(username, password);
        	
            if(doctorName != null) {
            	logger.info("Doctor Login Successfull...");
                return doctorName;
            } else {
            	logger.info("Incorrect Username & Password!");
                return null;
            }
        } catch (SQLException e) {
        	logger.info(e.getMessage());
        }
        return null;
    }


}
