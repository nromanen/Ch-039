package com.hospitalsearch.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author Pavel Kuz
 * edited Oleksandr Mukonin
 *
 */
@Embeddable
public class HospitalAddress {

	@NotEmpty
	@Size(max = 20)
	private String country;

	@NotEmpty
	@Size(max = 20)
	private String city;

	@Size(max = 20)
	private String street;

	/**
	 * House number on street
	 */
	@Size(max = 5)
	private String number;

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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
