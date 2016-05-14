package com.hospitalsearch.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Role implements Serializable {


	@Id
	@GeneratedValue
	private Long id;
	private String role;
	
	
	public Role() {}
	
	public Role(String role) {
		super();
		this.role = role;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	

}
