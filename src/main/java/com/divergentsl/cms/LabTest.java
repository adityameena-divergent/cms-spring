package com.divergentsl.cms;

import java.sql.*;
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

import com.divergentsl.cms.dao.LabTestDao;
import com.divergentsl.dto.LabTestDto;
import com.divergentsl.dto.PatientDto;

@Component
public class LabTest {

	private static Logger logger = LoggerFactory.getLogger(LabTest.class);

	@Autowired
	private LabTestDao labTestDao;

	/**
	 * This method takes a input & redirect it to method that admin want to perform
	 * the operation.
	 */
	public void labTestPanel() {
		Exit: while (true) {

			printTestOptions();

			Scanner sc = new Scanner(System.in);
			String input = sc.nextLine();

			switch (input) {

			case "1":
				addNewTest();
				break;

			case "2":
				readTest();
				break;

			case "3":
				listTest();
				break;

			case "4":
				deleteTest();
				break;

			case "5":
				updateTest();
				break;

			case "6":
				break Exit;

			default:
				logger.info("Invalid Input!");
				break;

			}
		}
	}

	/**
	 * This method print all the operation that admin can perform on lab
	 */
	private void printTestOptions() {

		System.out.println("\n----Lab Test Panel----");
		System.out.println("1. Add New Test");
		System.out.println("2. Read Test");
		System.out.println("3. List All Tests");
		System.out.println("4. Delete Test");
		System.out.println("5. Update Test");
		System.out.println("6. Exit");
		System.out.print("Enter Your Choice: ");
	}

	/**
	 * This method is used for insert new test data of patient
	 */
	public void addNewTest() {

		Scanner sc = new Scanner(System.in);

		LabTestDto labTestDto = new LabTestDto();

		try {

			System.out.println("\n----Adding Lab Test Data----");
			System.out.print("Enter Patient Id: ");
			String patientId = sc.nextLine();

			labTestDto.setPatientId(Integer.parseInt(patientId));

			System.out.print("\nEnter Test Name: ");
			String testName = sc.nextLine();

			labTestDto.setTestName(testName);

			System.out.print("\nEnter Test Fee: ");
			String fee = sc.nextLine();

			labTestDto.setFee(Integer.parseInt(testName));
			
			if(validateLabTest(labTestDto)) {
				return;
			}

			int i = labTestDao.add(patientId, testName, fee);
			if (i > 0) {
				logger.info("New Test Added Successfully...");
			} else {
				logger.info("Test Add Unsuccessfull!");
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
	}

	/**
	 * This method print the test data of input patient id
	 */
	private void readTest() {

		System.out.println("\n----Read Lab Test----");
		System.out.print("Enter Test Id: ");

		Scanner sc = new Scanner(System.in);
		String testId = sc.nextLine();

		try {

			Map<String, String> data = labTestDao.search(testId);

			if (data.size() != 0) {
				System.out.println("Test id: " + data.get("testId"));
				System.out.println("Test Name: " + data.get("testName"));
				System.out.println("Patient Id: " + data.get("patientId"));
				System.out.println("Test Fee: " + data.get("fee"));
			} else {
				logger.info("Test Not Found!");
			}

		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
	}

	/**
	 * This method print all the test records of patient
	 */
	public void listTest() {

		System.out.println("\n----List Tests----");

		try {

			List<Map<String, String>> list = labTestDao.listTest();

			System.out.printf("%7s | %12s | %10s | %12s | %14s | %8s |", "Test Id", "Test Name", "Patient Id",
					"Patient Name", "Contact Number", "Test Fee");
			System.out.println(
					"\n--------------------------------------------------------------------------------------------");
			for (Map<String, String> data : list) {
				System.out.printf("%7s | %12s | %10s | %12s | %14s | %8s |\n", data.get("testId"), data.get("testName"),
						data.get("patientId"), data.get("patientName"), data.get("contactNumber"), data.get("fee"));
			}
			System.out.println(
					"--------------------------------------------------------------------------------------------");

		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
	}

	/**
	 * This method delete the test record of given input test id
	 */
	public void deleteTest() {

		System.out.print("\nEnter Test Id: ");
		Scanner sc = new Scanner(System.in);

		String testId = sc.nextLine();

		try {

			if (labTestDao.search(testId).size() != 0) {
				int i = labTestDao.delete(testId);
				if (i > 0) {
					logger.info("Test Data Deleted Successfully...");
				} else {
					logger.info("Test Data Deletion Unsuccessfull!");
				}
			} else {
				logger.info("Test Data Not Found!");
			}

		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
	}

	/**
	 * This method update the data of given input test id
	 */
	private void updateTest() {

		System.out.println("\n----Update Test Data----");
		System.out.print("Enter Test Id; ");
		Scanner sc = new Scanner(System.in);

		String testId = sc.nextLine();

		try {

			Map<String, String> data = labTestDao.search(testId);
			if (data.size() != 0) {

				System.out.println("\n----Previous Data----");
				System.out.println("Previous Test Name: " + data.get("testName"));
				System.out.println("Previous Patient Id: " + data.get("patientId"));
				System.out.println("Previous Patient Name: " + data.get("patientName"));
				System.out.println("Previous Test Fee: " + data.get("fee"));
				
				LabTestDto labTestDto = new LabTestDto();

				System.out.print("\nEnter New Test Name: ");
				String testName = sc.nextLine();
				data.put("testName", testName);
				
				labTestDto.setTestName(testName);

				System.out.print("\nEnter New Patient Id: ");
				String patientId = sc.nextLine();
				data.put("patientId", patientId);
				
				labTestDto.setPatientId(Integer.parseInt(testName));

				System.out.print("\nEnter New Test Fee: ");
				String fee = sc.nextLine();
				data.put("fee", fee);
				
				labTestDto.setFee(Integer.parseInt(fee));
				
				if(validateLabTest(labTestDto)) {
					return;
				}

				int i = labTestDao.update(data);

				if (i > 0) {
					logger.info("Test Data Update Successfully...");
				} else {
					logger.info("Test Data Update Unsuccessfull!");
				}

			} else {
				logger.info("Test Data Not Found!");
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
	}

	private boolean validateLabTest(LabTestDto test) {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<LabTestDto>> violations = validator.validate(test);

		for (ConstraintViolation<LabTestDto> violation : violations) {
			logger.error(violation.getMessage());
		}
		return violations.size() > 0;
	}

}
