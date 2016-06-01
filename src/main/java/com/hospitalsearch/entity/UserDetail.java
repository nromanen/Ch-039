package com.hospitalsearch.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.hospitalsearch.util.Gender;


@Entity
@Table(name = "userdetail")
public class UserDetail implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userdetail_gen")
	@SequenceGenerator(name = "userdetail_gen", sequenceName = "userdetail_id_seq")
	@Column(name="id")
	@JsonIgnore
	private Long id;
	private String firstName;
	private String lastName;
	private String phone;
	private LocalDate birthDate;
	@JsonIgnore
	private String imagePath;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private String address;

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL,mappedBy="userDetails")
	@Fetch(FetchMode.SELECT)
	private DoctorInfo doctorsDetails;

	@JsonIgnore
	@OneToOne
    @Fetch(FetchMode.SELECT)
	private PatientCard patientCard;
	
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

	public PatientCard getPatientCard() {
		return patientCard;
	}

	public void setPatientCard(PatientCard patientCard) {
		this.patientCard = patientCard;
	}

}
