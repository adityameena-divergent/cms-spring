package com.divergentsl.dto;

import javax.validation.constraints.Size;

public class DrugDto {

	@Size(min = 5, max = 30)
	private String name;
	
	@Size(min = 10, max = 250)
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
