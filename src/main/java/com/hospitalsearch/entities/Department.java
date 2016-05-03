package com.hospitalsearch.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Department implements Serializable{
	
	private static final long serialVersionUID = 2488180615002820167L;
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@ManyToMany
	@JoinTable(name="DEPARTMENT_USER",joinColumns=@JoinColumn(name="DEPARTMENT_ID"),inverseJoinColumns=@JoinColumn(name="USER_ID"))
	private List<User> doctors;
	@ManyToOne
	private Hospital hospital;
	
	public Department() {
		// TODO Auto-generated constructor stub
	}


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

	public List<User> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<User> doctors) {
		this.doctors = doctors;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
	
	
}
