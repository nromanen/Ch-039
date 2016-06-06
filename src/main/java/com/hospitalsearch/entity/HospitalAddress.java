package com.hospitalsearch.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import org.hibernate.search.annotations.Field;
import org.hibernate.validator.constraints.NotEmpty;

@Embeddable
public class HospitalAddress {

	@NotEmpty
	@Size(max = 30)
	@Field
	private String country;

	@NotEmpty
	@Size(max = 30)
	@Field
	private String city;

	@Size(max = 30)
	@Field
	private String street;

	@Size(max = 5)
	private String building;

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

	public String getBuilding() {
		return building;
	}


	public void setBuilding(String building) {
		this.building = building;
	}


	@Override
	public String toString() {
		return  new StringBuilder()
				.append(street).append(", ")
				.append(building).append(", ")
				.append(city).append(", ")
				.append(country).toString();
	}	

}
