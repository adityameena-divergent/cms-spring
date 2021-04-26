package com.divergentsl.cms;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.divergentsl.cms.dao.PatientDao;
import com.divergentsl.cms.dto.PatientDto;

@Component
public class CRUDPatient {

	
	@Autowired
	private PatientDao patientDao;
	
	private static Logger logger = LoggerFactory.getLogger(CRUDPatient.class);
	
	
	/**
	 * This method takes all the input data of patient and update it.
	 */
	public void updatePatient() {

		System.out.println("\n----Update Patient Data----");
		System.out.print("Enter Patient Id: ");

		Scanner sc = new Scanner(System.in);
		String patientId = sc.nextLine();

		try {

			Map<String, String> data = patientDao.search(patientId);

			if (data.size() == 0) {
				logger.info("Patient not found...");
				return;
			}

			System.out.println("\nPatient Id: " + patientId);
			System.out.println("Patient Name: " + data.get("name"));
			System.out.println("Patient Age: " + data.get("age"));
			System.out.println("Patient Weight: " + data.get("weight"));
			System.out.println("Patient Gender: " + data.get("gender"));
			System.out.println("Patient Contact Number: " + data.get("contactNumber"));
			System.out.println("Patient Address: " + data.get("address"));
			
			PatientDto patientDto = new PatientDto();

			System.out.print("\nEnter New Name: ");
			String name = sc.nextLine();
			data.put("name", name);
			
			patientDto.setPatientName(name);
			
			System.out.print("\nEnter New Age: ");
			String age = sc.nextLine();
			data.put("age", age);
			
			patientDto.setAge(Integer.parseInt(age));
			
			System.out.print("\nEnter New Weight: ");
			String weight = sc.nextLine();
			data.put("weight", weight);
			
			patientDto.setWeight(Integer.parseInt(weight));
			
			System.out.print("\nEnter New Gender: ");
			String gender = sc.nextLine();
			data.put("gender", gender);
			
			patientDto.setGender(gender);
			
			System.out.print("\nEnter New Contact Number: ");
			String contactNumber = sc.nextLine();
			data.put("contactNumber", contactNumber);
			
			patientDto.setContactNumber(Integer.parseInt(contactNumber));
			
			System.out.print("\nEnter New Address: ");
			String address = sc.nextLine();
			data.put("address", address);
			
			data.put("id", patientId);
			
			if(validatePatient(patientDto)) {
				return;
			}

			int i = patientDao.update(data);

			if (i != 0) {
				logger.info("Data Update Successfully...");
			} else {
				logger.info("Data update unsucessfull...");
			}

		} catch (SQLException | NumberFormatException e) {

			logger.info(e.getMessage());
		}

	}

	/**
	 * This method takes patient id & remove it from database.
	 */
	public void deletePatient() {

		System.out.println("\n----Delete Patient Data----");
		System.out.print("Enter Patient Id: ");
		Scanner sc = new Scanner(System.in);
		String patientId = sc.nextLine();

		try {


			if (patientDao.search(patientId).size() != 0)
				if (patientDao.delete(patientId) > 0) {
					logger.info("Patient deleted successfully...");
				} else {
					logger.info("Patient deletion unsuccessful...");
				}
			else
				logger.info("Patient not found...");

		} catch (SQLException e) {
			logger.info("Problem with database");
		}

	}

	/**
	 * This method print all the operation that admin can perform on patient.
	 */
	public void printPatientOptions() {
		System.out.println("\n\n----Patient Panel----");
		System.out.println("1. Add new patient");
		System.out.println("2. Update patient data");
		System.out.println("3. List all patient data");
		System.out.println("4. Search patient");
		System.out.println("5. Delete patient");
		System.out.println("6. Back");
		System.out.print("Enter your choice: ");
	}

	/**
	 * This method takes a input & redirect it to specific that admin want to
	 * perform operation.
	 */
	public void CRUDOperation() {
		CRUD: while (true) {
			printPatientOptions();

			Scanner sc = new Scanner(System.in);
			String input = sc.nextLine();

			switch (input) {

			case "1":
				insert();
				break;

			case "2":
				updatePatient();
				break;

			case "3":
				listAll();
				break;

			case "4":
				search();
				break;

			case "5":
				deletePatient();
				break;

			case "6":
				break CRUD;

			default:
				logger.info("Invalid Input!");
				break;
			}

		}
	}

	/**
	 * This method takes input all patient data and insert into database.
	 */
	public void insert() {

	
		try {
			Scanner sc = new Scanner(System.in);
			Map<String, String> map = new HashMap<>();

			PatientDto patient = new PatientDto();
			
			System.out.println("\n----Add Patient----");
			System.out.print("Enter Patient Name: ");
			String patient_name = sc.nextLine();
			map.put("patient_name", patient_name);
			
			patient.setPatientName(patient_name);
			

			System.out.print("\nEnter Age: ");
			String age = sc.nextLine();
			map.put("age", age);
			
			patient.setAge(Integer.parseInt(age));

			System.out.print("\nEnter Weight: ");
			String weight = sc.nextLine();
			map.put("weight", weight);

			patient.setWeight(Integer.parseInt(weight));
			
			System.out.print("\nEnter Gender: ");
			String gender = sc.nextLine();
			map.put("gender", gender);
			
			patient.setGender(gender);

			System.out.print("\nEnter Contact Number: ");
			String contact_number = sc.nextLine();
			map.put("contact_number", contact_number);

			patient.setContactNumber(Integer.parseInt(contact_number));
			
			System.out.print("\nEnter Address: ");
			String address = sc.nextLine();
			map.put("address", address);
			
			patient.setAddress(address);

			if(validatePatient(patient)) {
				return;
			}
			
			
			if (patientDao.insert(map) > 0) {
				logger.info("Data Inserted Successfully...");
			} else {
				logger.info("Data Insert Unsucessful...");
			}
		} catch (SQLException | NumberFormatException e) {
			logger.info(e.getMessage());
		}
	}

	/**
	 * This method takes patient id & print all the data of that patient
	 */
	public void search() {

	
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("\n\n----Search Patient----");
			System.out.print("\nEnter Patient Id: ");
			String patientId = sc.nextLine();


			Map<String, String> data = patientDao.search(patientId);

			if (data.size() != 0) {
				System.out.println("\nPatient Id: " + data.get("id"));
				System.out.println("Patient Name: " + data.get("name"));
				System.out.println("Age: " + data.get("age"));
				System.out.println("Weight: " + data.get("weight"));
				System.out.println("Gender: " + data.get("gender"));
				System.out.println("Contact Number: " + data.get("contactNumber"));
				System.out.println("Address: " + data.get("address"));
			} else {
				logger.info("Data Not Found!");
			}

		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
	}

	
	/**
	 * This method print all the records of patient
	 */
	public void listAll() {

		try {

			List<Map<String, String>> list = patientDao.listAll();

			if (!list.isEmpty()) {
				System.out.println("\n\n----Patient List----\n");
				System.out.printf("%10s  %15s     %3s     %6s     %6s   %14s  %15s\n", "Patient_Id", "Patient_Name",
						"Age", "Weight", "Gender", "Contact_Number", "Address");
				System.out.println(
						"----------------------------------------------------------------------------------------------------------------");

				for (Map<String, String> record : list) {
					System.out.printf("%10s  %15s     %3s     %6s     %6s   %14s  %15s\n", record.get("id"),
							record.get("name"), record.get("age"), record.get("weight"), record.get("gender"),
							record.get("contactNumber"), record.get("address"));
				}
				System.out.println(
						"----------------------------------------------------------------------------------------------------------------");
			} else {
				logger.info("Data Not Found!");
			}

		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
	}
	
	
	private boolean validatePatient(PatientDto patient) {
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		Set<ConstraintViolation<PatientDto>> violations = validator.validate(patient);
		
		for (ConstraintViolation<PatientDto> violation : violations) {
			logger.error(violation.getMessage());
			
		}
		
		return violations.size() > 0;
	}
	

}
