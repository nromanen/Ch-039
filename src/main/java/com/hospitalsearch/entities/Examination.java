package com.hospitalsearch.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Examination implements Serializable {

	private static final long serialVersionUID = -5826917082480506729L;

	@Id
	@GeneratedValue
	private Long id;
	private String examination;
	private Boolean pass;
	
	public Examination() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExamination() {
		return examination;
	}

	public void setExamination(String examination) {
		this.examination = examination;
	}

	public Boolean getPass() {
		return pass;
	}

	public void setPass(Boolean pass) {
		this.pass = pass;
	}
	

	
}
