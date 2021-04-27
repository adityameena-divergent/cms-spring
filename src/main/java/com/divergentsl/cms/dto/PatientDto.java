package com.divergentsl.cms.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PatientDto {
	
	private String id;

	@NotNull(message = "Name cannot be null")
	@Size(min = 5, max = 20, message = "Name must be between 10 and 20 characters")
	private String patientName;
	
	@Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 150, message = "Age should not be greater than 150")
	private int age;
	
	@Min(value = 20, message = "Age should not be less than 20")
    @Max(value = 250, message = "Age should not be greater than 250")
	private int weight;
	
	private String gender;
	
	@Min(value = 10, message = "Number Should be 10")
    @Max(value = 10, message = "Number Should be 10")
	private int contactNumber;
	
	private String address;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(int contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
}
