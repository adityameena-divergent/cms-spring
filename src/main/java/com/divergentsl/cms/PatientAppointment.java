package com.divergentsl.cms;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.divergentsl.cms.dao.DoctorDao;
import com.divergentsl.cms.dao.PatientAppointmentDao;
import com.divergentsl.cms.dao.PatientDao;

@Component
public class PatientAppointment {
	
	private static Logger logger = LoggerFactory.getLogger(PatientAppointment.class);
	
	
	@Autowired
	private PatientDao patientDao;
	@Autowired
	private DoctorDao doctorDao;
	@Autowired
	private PatientAppointmentDao patientAppointmentDao;
	
	
	

	/**
	 * This method take patient and take the doctor_id that which doctor he is
	 * suitable according to patient problem.
	 */

	/**
	 * This method helper method for makeAppointment() It takes input and return Map
	 * of input data.
	 * 
	 * @return Map
	 */
	public void makeAppointment() {

		Map<String, String> map = new HashMap<>();
		Scanner sc = new Scanner(System.in);

		System.out.println("\n\n----Make Appointment----");

		System.out.print("Enter Patient_Id : ");
		String patientId = sc.nextLine();
		map.put("patientId", patientId);

		System.out.print("\nEnter Doctor Id : ");
		String doctorId = sc.nextLine();
		map.put("doctorId", doctorId);

		System.out.print("\nEnter Patient Problem : ");
		String problem = sc.nextLine();
		map.put("problem", problem);

		System.out.print("\nEnter Appointment Date : ");
		String appointmentDate = sc.nextLine();
		map.put("appointmentDate", appointmentDate);

		System.out.print("Appointment Time : ");
		String appointmentTime = sc.nextLine();
		map.put("time", appointmentTime);

		try {

			if (patientDao.search(patientId).size() == 0 || doctorDao.searchById(doctorId).size() == 0) {
				
				logger.debug("Patient or Doctor Data Not Found!");
				
			} else {
				int i = patientAppointmentDao.makeAppointment(map);
				if(i > 0) {
					logger.debug("Appointment Created Successfully...");
				} else {
					logger.debug("Appointment Creation Unsuccessfull!");
				}
			}

		} catch (SQLException e) {
			logger.debug(e.getMessage());
		}
	}

	
	/**
	 * This method print all patient appointment that has been appoint to that doctor whose currently logged in.
	 * @param doctorId
	 * @param dname
	 */
	public void patientAppointToYou(String doctorId, String dname) {

		try {

			System.out.println("----Login as " + dname + "----");
			System.out.println("----Patients Appoints to You----");
			System.out.printf("\n%18s   | %10s   | %12s   | %16s   | %9s   | %20s", "Appointment_Number", "Patient_Id",
					"Patient_Name", "Appointment_Date", "Time", "Problem");
			System.out.println(
					"\n---------------------------------------------------------------------------------------------------------------");


			List<Map<String, String>> listOfPatients = patientAppointmentDao.patientAppointToYou(doctorId);

			for (Map<String, String> record : listOfPatients) {

				System.out.printf("%18s   | %10s   | %12s   | %16s   | %9s   | %20s\n", record.get("appointmentNumber"),
						record.get("id"), record.get("name"), record.get("appointmentDate"), record.get("time"),
						record.get("problem"));

			}

			System.out.println(
					"---------------------------------------------------------------------------------------------------------------");

		} catch (SQLException e) {
			logger.debug(e.getMessage());
		}
	}

	/**
	 * This method print all the patient appointment
	 * @param doctorName
	 */
	public void allPatientList(String doctorName) {

		try {

			System.out.println("----Login as " + doctorName + "----");
			System.out.println("----List of all patient----");
			System.out.printf("%18s   | %16s   | %8s   | %10s   | %12s   | %6s   | %20s   | %9s   | %15s   |",
					"Appointment_Number", "Appointment_Date", "Time", "Patient_Id", "Patient_Name", "Gender", "Problem",
					"Doctor_Id", "Doctor_Name");
			System.out.println(
					"\n--------------------------------------------------------------------------------------------------------------------------------------------------------------");

			
			List<Map<String, String>> listOfPatient = patientAppointmentDao.allPatientList();
			for (Map<String, String> record : listOfPatient) {
				System.out.printf("%18s   | %16s   | %8s   | %10s   | %12s   | %6s   | %20s   | %9s   | %15s   |\n",
						record.get("appointmentNumber"), record.get("appointmentDate"), record.get("time"),
						record.get("patientId"), record.get("patientName"), record.get("gender"), record.get("problem"),
						record.get("doctorId"), record.get("doctorName"));
			}

			System.out.println(
					"--------------------------------------------------------------------------------------------------------------------------------------------------------------");
		}

		catch (SQLException e) {
			logger.debug(e.getMessage());
		}
	}

	/**
	 * This method is used to add prescription of given appointment number
	 */
	public void addPrescription() {

		Scanner sc = new Scanner(System.in);

		System.out.println("\n----Add Prescription----");

		System.out.print("Enter Appointment Number: ");
		String appointmentNumber = sc.nextLine();


		try {
			Map<String, String> data = patientAppointmentDao.checkPatient(appointmentNumber);
			if (data.size() != 0) {

				System.out.print("\nEnter Prescription: ");
				String prescription = sc.nextLine();

				System.out.print("\nEnter Notes: ");
				String notes = sc.nextLine();

				data.put("appointmentNumber", appointmentNumber);
				data.put("prescription", prescription);
				data.put("notes", notes);

				int i = patientAppointmentDao.addPrescription(data);

				if(i > 0) {
					logger.debug("Prescription Added Successfully...");
				} else {
					logger.debug("Prescription Add Unsuccessfull!");
				}
				
			} else {
				logger.debug("Appointment Data Not Found!");
			}
		} catch (SQLException e) {
			logger.debug(e.getMessage());
		}
	}

	/**
	 * This method print all the appointment of given patient
	 */
	public void checkPatientHistory() {

		try {

			System.out.println("\n----Check Patient History----");
			System.out.print("\nEnter Patient Id: ");
			String patientId = new Scanner(System.in).nextLine();


			List<Map<String, String>> list = patientAppointmentDao.patientHistory(patientId);

			if (list.size() > 0) {
				System.out.printf("\n%10s | %15s | %10s | %18s | %16s | %9s | %20s | %25s", "Patient Id",
						"Patient Name", "Problem", "Appointment Number", "Appointment Date", "Doctor Id",
						"Prescription", "Notes");
				System.out.println(
						"\n--------------------------------------------------------------------------------------------------------------------------------------------------");

				for (Map<String, String> record : list) {
					System.out.printf("\n%10s | %15s | %10s | %18s | %16s | %9s | %20s | %25s", record.get("patientId"),
							record.get("patientName"), record.get("problem"), record.get("appointmentNumber"),
							record.get("appointmentDate"), record.get("doctorId"), record.get("prescription"),
							record.get("notes"));
				}

				System.out.println(
						"\n--------------------------------------------------------------------------------------------------------------------------------------------------");
			} else {
				logger.debug("Patient Not Found!");
			}

		} catch (SQLException e) {
			logger.debug(e.getMessage());
		}
	}

	/**
	 * This method generate & print invoice of given patient according to their appointment 
	 */
	public void generateInvoice() {
		System.out.print("\nEnter Patient Id: ");
		String patientId = new Scanner(System.in).nextLine();
		try {

			Map<String, String> invoice = patientAppointmentDao.generateInvoice(patientId);

			if (invoice.size() != 0) {
				System.out.println("\n--------------------Invoice--------------------");
				System.out.printf("%12s : %10s \t %10s : %4s\n", "Patient Name", invoice.get("patientName"),
						"Patient Id", invoice.get("patientId"));
				System.out.printf("%12s : %10s \t %10s : %10s\n", "Doctor Name", invoice.get("doctorName"), "Problem",
						invoice.get("problem"));
				System.out.printf("%12s : %10s \t %10s : %4s rs\n", "Date", invoice.get("appointmentDate"), "Total",
						invoice.get("fee"));
				System.out.println("-----------------------------------------------");
			} else {
				logger.debug("Patient Not Found!");
			}
		} catch (SQLException e) {
			logger.debug(e.getMessage());
		}
	}

}
