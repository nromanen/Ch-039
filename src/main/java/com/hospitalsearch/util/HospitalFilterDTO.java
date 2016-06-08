package com.hospitalsearch.util;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotEmpty;

public class HospitalFilterDTO {
	@Size(max=60)
	private String country;
	@Size(max=60,min=4)
	@NotEmpty
	private String name;
	@Size(max=60)
	private String city;
	
	public HospitalFilterDTO() { }

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	
}
