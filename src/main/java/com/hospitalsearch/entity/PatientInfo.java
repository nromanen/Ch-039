package com.hospitalsearch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "patientinfo")
public class PatientInfo{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patientinfo_gen")
	@SequenceGenerator(name = "patientinfo_gen", sequenceName = "patientinfo_id_seq", initialValue = 1, allocationSize = 1)
	private Long id;

	@Column(name="cardid")
	private String cardId;

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
