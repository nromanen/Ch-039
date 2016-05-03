package com.hospitalsearch.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hospitalsearch.utils.Gender;



public class UserDetail implements Serializable{
	
	private static final long serialVersionUID = 4146890052089898735L;
	
	@Id
	@GeneratedValue
	private Long id;
	private String firstName;
	private String lastName;
	private String phone;
	private LocalDate birthDate;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private String address;
	
	public UserDetail() {
		// TODO Auto-generated constructor stub
	}

	public UserDetail(String firstName, String lastName, String phone, LocalDate birthDate, Gender gender,
			String address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.birthDate = birthDate;
		this.gender = gender;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
