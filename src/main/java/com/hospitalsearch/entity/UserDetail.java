package com.hospitalsearch.entity;

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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.hospitalsearch.util.Gender;


@Entity
@Table(name = "userdetail")
@Indexed
@Cache(region="entityCache",usage=CacheConcurrencyStrategy.READ_WRITE)
public class UserDetail{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userdetail_gen")
	@SequenceGenerator(name = "userdetail_gen", sequenceName = "userdetail_id_seq", initialValue = 1, allocationSize = 1)
	@JsonIgnore
	private Long id;
	
	@Column(name="firstname")
	@Field
	private String firstName;
	
	@Column(name="lastname")
	@Field
	private String lastName;
	
	private String phone;
	
	@Column(name="birthdate")
	private LocalDate birthDate;
	
	@JsonIgnore	
	@Column(name="imagepath")
	private String imagePath;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private String address;

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL,mappedBy="userDetails")
	@Fetch(FetchMode.SELECT)
	@ContainedIn
	private DoctorInfo doctorsDetails;

	@JsonIgnore
	@OneToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="patientcard_id")
	private PatientCard patientCard;

	public UserDetail() {}


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


	@Override
	public String toString() {
		return "UserDetail{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				'}';
	}

}
