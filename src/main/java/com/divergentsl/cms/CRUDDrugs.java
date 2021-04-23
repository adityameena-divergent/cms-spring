package com.divergentsl.cms;

import java.sql.*;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.divergentsl.cms.dao.DrugDao;

@Component
public class CRUDDrugs {

	private static Logger logger = LoggerFactory.getLogger(CRUDDrugs.class);
	
	
	@Autowired
	private DrugDao drugDao;
	
	
	
	
	/**
	 * This method takes input from user and redirect it to the operation that he
	 * wants to perform.
	 */
	public void CRUDOperations() {
		CRUD: while (true) {

			printDrugOption();

			Scanner sc = new Scanner(System.in);
			String input = sc.nextLine();

			switch (input) {
			case "1":
				addDrug();
				break;

			case "2":
				searchDrug();
				break;

			case "3":
				deleteDrug();
				break;

			case "4":
				updateDrug();
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
	 * This method print the operation name that admin can perform on drugs
	 */
	public void printDrugOption() {
		System.out.println("\n\n----Drugs----");
		System.out.println("1. Add new drugs");
		System.out.println("2. Search drugs");
		System.out.println("3. Delete drugs");
		System.out.println("4. Update drugs");
		System.out.println("5. Back");
		System.out.print("Enter your choice: ");
	}
	

	/**
     * This method update the existing data of drugs
     */
    public void updateDrug() {

        System.out.println("\n----Update Drug Data----");
        System.out.print("Enter Drug Id: ");
        Scanner sc = new Scanner(System.in);
        String id = sc.nextLine();
        
        try {
        	        
        	
        	Map<String, String> data = drugDao.search(id);

        	if (data.size() == 0) {
        		logger.info("Drug Not Found!");
        	} else {
	            System.out.println("\nDrug Id: " + id);
	            System.out.println("Previous Drug Name: " + data.get("name"));
	            System.out.println("Previous Drug Description: " + data.get("description"));
	
	            System.out.println("\nEnter New Drug Name: ");
	            String name = sc.nextLine();
	
	            System.out.print("\nEnter New Description: ");
	            String description = sc.nextLine();
	            
	            data.put("name", name);
	            data.put("description", description);
	            drugDao.update(data);
        	}
        } catch(SQLException e) {
        	logger.info(e.getMessage());
        }
    }

    
	/**
	 * This method takes drug_id and remove it from database.
	 */
	public void deleteDrug() {
		System.out.println("\n----Delete Drug----");
		System.out.print("Enter Drug Id: ");
		Scanner sc = new Scanner(System.in);
		String id = sc.nextLine();

		try {

			if (drugDao.search(id).size() != 0) {
				int i = drugDao.delete(id);
				if(i > 0) {
					logger.info("Drug Deleted Successfully...");
				} else {
					logger.info("Drug Deletion Unsuccessfull!");
				}
			} else {
				logger.info("Drug Not Found!");
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}

	}

	
	/**
	 * This method print the data of input drug_id
	 */
	public void searchDrug() {
		System.out.println("\n----Search Drug----");
		System.out.println("Enter Drug Id: ");
		Scanner sc = new Scanner(System.in);

		String id = sc.nextLine();

		try {
			
			Map<String, String> data = drugDao.search(id);

			if (data.size() != 0) {
				System.out.println("\nDrug Id: " + data.get("id"));
				System.out.println("Drug Name: " + data.get("name"));
				System.out.println("Description: " + data.get("description"));
			} else {
				logger.info("Drug Not Found!");
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
	}

	/**
	 * This method takes input drug data from user and insert it into database.
	 */
	public void addDrug() {

		System.out.println("\n----Add new drug----");
		System.out.println("Enter Drug Name: ");

		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine();

		System.out.print("\nEnter Drug Description: ");
		String description = sc.nextLine();

		try {

			int i = drugDao.add(name, description);
			if (i > 0) {
				logger.info("Drug Added Successfully...");
			} else {
				logger.info("Drug Add Unsucessful!");
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
	}

}
