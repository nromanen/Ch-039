package com.hospitalsearch.entity;

import com.hospitalsearch.util.Gender;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


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

	@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE},fetch = FetchType.EAGER,mappedBy="userDetails")
	@Fetch(FetchMode.SELECT)
	private DoctorInfo doctorsDetails;

	@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE},fetch = FetchType.EAGER)
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
