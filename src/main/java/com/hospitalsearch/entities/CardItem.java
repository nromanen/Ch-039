package com.hospitalsearch.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class CardItem implements Serializable {
	
	
	private static final long serialVersionUID = -1407036211724471026L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private LocalDate date;
	@ManyToOne
	PatientCard patientCard;
	
	@OneToMany
	private List<Examination> examinationList;
	private String result;
	private String prescription;
	@ManyToOne
	private User doctor;
	@ManyToOne
	private PatientCard card;

	public CardItem() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public PatientCard getPatientCard() {
		return patientCard;
	}

	public void setPatientCard(PatientCard patientCard) {
		this.patientCard = patientCard;
	}

	public List<Examination> getExaminationList() {
		return examinationList;
	}

	public void setExaminationList(List<Examination> examinationList) {
		this.examinationList = examinationList;
	}

	public String getPrescription() {
		return prescription;
	}

	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	public User getDoctor() {
		return doctor;
	}

	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}

	public PatientCard getCard() {
		return card;
	}

	public void setCard(PatientCard card) {
		this.card = card;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
	
}
