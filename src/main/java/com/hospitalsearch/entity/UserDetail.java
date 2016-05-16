package com.hospitalsearch.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.hospitalsearch.util.Gender;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class UserDetail implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	private String firstName;
	private String lastName;
	private String phone;
	private LocalDate birthDate;
	private String imagePath;

	@Enumerated(EnumType.STRING)
	private Gender gender;
	private String address;

	@OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL,mappedBy="userDetails")
	@Fetch(FetchMode.SELECT)
	private DoctorInfo doctorsDetails;

	@OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private PatientInfo patientsDetails;

	public UserDetail() {

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

	public PatientInfo getPatientsDetails() {
		return patientsDetails;
	}
	public void setPatientsDetails(PatientInfo patientsDetails) {
		this.patientsDetails = patientsDetails;
	}

	public DoctorInfo getDoctorsDetails() {
		return doctorsDetails;
	}
	public void setDoctorsDetails(DoctorInfo doctorsDetails) {
		this.doctorsDetails = doctorsDetails;
	}

	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
