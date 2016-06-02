package com.hospitalsearch.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "department")
public class Department implements Serializable{
	
	private static final long serialVersionUID = 2488180615002820167L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_gen")
	@SequenceGenerator(name = "department_gen", sequenceName = "department_id_seq")
	private Long id;
	private String name;
	
	@ManyToMany(cascade=CascadeType.ALL)
	private List<DoctorInfo> doctors;
	
	@ManyToOne
	private Hospital hospital;
	private String imagePath;
	
	public Department() { 	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DoctorInfo> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<DoctorInfo> doctors) {
		this.doctors = doctors;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
