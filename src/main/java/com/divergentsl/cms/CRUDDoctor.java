package com.divergentsl.cms;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.divergentsl.cms.dao.DoctorDao;

@Component
public class CRUDDoctor {

	
	private static Logger logger = LoggerFactory.getLogger(CRUDDoctor.class);
	
	@Autowired
	private DoctorDao doctorDao;
	
	
	
	
	/**
	 * This method takes input from user & redirect it to specific method which he want perform operations on doctor
	 */
	public void CRUDOperation() {
		CRUD: while (true) {

			printCRUDDoctorOption();
			Scanner sc = new Scanner(System.in);

			String input = sc.nextLine();

			switch (input) {

			case "1":
				this.insertDoctor();
				break;

			case "2":
				this.listDoctors();
				break;

			case "3":
				this.updateDoctor();
				break;

			case "4":
				this.deleteDoctor();
				break;

			case "5":
				break CRUD;

			default:
				logger.info("Invalid Input!");
				break;
			}
		}
	}
	
	/**
	 * This method print all the operations that can perform on doctor
	 */
	private void printCRUDDoctorOption() {
		System.out.println("\n\n----Edit DOCTOR----");
		System.out.println("1. Insert a new doctor");
		System.out.println("2. List all doctors");
		System.out.println("3. Update doctor data");
		System.out.println("4. Remove doctor");
		System.out.println("5. Back");
		System.out.println("Enter your choice: ");
	}

	
	/**
	 * This method takes doctor id and remove it from record
	 */
	public void deleteDoctor() {
		

		System.out.println("\n----Remove Doctor----");
		System.out.print("Enter Doctor Id: ");
		Scanner sc = new Scanner(System.in);
		String doctor_id = sc.nextLine();


		try {
			if (doctorDao.searchById(doctor_id).size() == 0) {
				logger.info("Doctor Not Found!");
			} else {

				try {

					doctorDao.delete(doctor_id);
					logger.info("Data Deleted Successfully...");

				} catch (SQLException e) {
					logger.info("Data Deletion Unsuccessful...");
				}
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
	}

	/**
	 * This method takes doctor id and new data from user & update the doctor data
	 */
	public void updateDoctor() {

		System.out.println("\n----Update Doctor----");
		System.out.print("\nEnter Doctor Id : ");

		Scanner sc = new Scanner(System.in);

		String did = sc.nextLine();
		
		Map<String, String> map = null;
		try {
			map = doctorDao.searchById(did);
		} catch (SQLException e) {
			logger.info(e.getMessage());
			return;
		}

		if (map == null || map.size() == 0) {
			logger.info("Doctor Not Found!");
		} else {

			printPreviousDataOfDoctor(map);

			System.out.print("\nEnter New Doctor Name : ");
			map.put("dname", sc.nextLine());
			System.out.print("\nEnter New Doctor Speciality : ");
			map.put("speciality", sc.nextLine());

			try {
				if(doctorDao.update(map) > 0) {
					logger.info("Data Update Successfully...");
				} else {
					logger.info("Data update unsucessful!");
				}
			} catch (SQLException e) {
				logger.info(e.getMessage());
			}
		}
	}

	
	/**
	 * This method print the previous data before giving the input for update data.
	 * @param map of doctor data.
	 */
	public void printPreviousDataOfDoctor(Map<String,String> map) {
		System.out.println("\n----Update Doctor Data----");
		System.out.println("Doctor Id : " + map.get("did"));
		System.out.println("Previous Doctor Name : " + map.get("dname"));	
		System.out.println("Previous Doctor Speciality : " + map.get("speciality"));
	}

	/**
	 * This method print all the doctor record present in database.
	 */
	public void listDoctors() {
		
		try {

			
			List<Map<String, String>> doctorList = doctorDao.list();
			
			String did = "DoctorId";
			String dname = "Doctor Name";
			String speciality = "Speciality";

			System.out.printf("%10s%15s%15s\n", did, dname, speciality);
			System.out.println("------------------------------------------------");
			
			for (Map<String, String> aDoctor : doctorList) {
				System.out.printf("%10s%15s%15s\n", aDoctor.get(DoctorDao.ID), aDoctor.get(DoctorDao.NAME), aDoctor.get(DoctorDao.SPECIALITY));
			}
			
			System.out.println("-------------------------------------------------");

		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
	}

	/**
	 * This method takes input from user for insertint new doctor data & pass it to helper method as a Map for insert the data into database.
	 */
	public void insertDoctor() {
		Scanner sc = new Scanner(System.in);

		System.out.println("\n\n--Insert doctor details--");
		System.out.print("Enter Name: ");
		String dname = sc.nextLine();

		System.out.print("\nEnter Username: ");
		String username = sc.nextLine();

		System.out.print("\nEnter Password: ");
		String password = sc.nextLine();

		System.out.print("\nEnter Speciality: ");
		String speciality = sc.nextLine();

		try {
			int i = doctorDao.insert(dname, username, password, speciality);
			if (i > 0) {
				logger.info("Data Inserted Successfully...");
			} else {
				logger.info("Data Insertion Unsucessful!");
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}

	}

	

}
