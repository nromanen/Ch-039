package com.hospitalsearch.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "patientinfo")
public class PatientInfo implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patientinfo_gen")
	@SequenceGenerator(name = "patientinfo_gen", sequenceName = "patientinfo_id_seq")
	private Long id;

	private String cardId;

	public PatientInfo() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
}
