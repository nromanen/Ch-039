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
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hospitalsearch.service.annotation.Date;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.hospitalsearch.util.Gender;
import org.springframework.format.annotation.DateTimeFormat;


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
    @Pattern(regexp = "^[A-Z][a-z]+$",message = "Not valid. Ex: Alina")
	@Field
	private String firstName;

	@Column(name="lastname")
    @Pattern(regexp = "^[A-Z][a-z]+$",message = "Not valid. Ex: Veter")
	@Field
	private String lastName;
	@Pattern(regexp = "^\\+38 \\(\\d{3}\\) \\d{3}-\\d{4}", message = "Not valid. Ex: +38 (095) 435-7132")
	private String phone;
	
	@Column(name="birthdate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Date(message = "Not valid format")
	private LocalDate birthDate;
	
	@JsonIgnore	
	@Column(name="imagepath")
	private String imagePath;

	@Enumerated(EnumType.STRING)
    @com.hospitalsearch.service.annotation.Gender(message = "Not valid format")
	private Gender gender;
	
	private String address;

	@OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL,mappedBy="userDetails")
	@Fetch(FetchMode.SELECT)
	@ContainedIn
	@JsonIgnore
	private DoctorInfo doctorsDetails;

	@OneToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="patientcard_id")
	@JsonIgnore
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
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
