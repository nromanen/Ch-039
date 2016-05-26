package com.hospitalsearch.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.NotEmpty;

@Embeddable
public class HospitalAddress {
	@NotEmpty
	@Max(value=100)
	private String city;
	@NotEmpty
	@Max(value=100)
	private String country;
	@Max(value=100)
	private String street;

	public HospitalAddress(){}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
}
