package com.divergentsl.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DoctorDto {

	@NotNull
	@Size(min = 5, max = 20, message = "Name must be between 5 and 20 characters.")
	private String name;
	
	@NotNull
	@Size(min = 6, max = 15, message = "Name must be between 6 and 15 characters.")
	private String username;
	
	@NotNull
	@Size(min = 8, max = 20, message = "Name must be between 8 and 20 characters.")
	private String password;
	
	@NotNull
	private String speciality;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	
	
	
}
