package com.hospitalsearch.entity;

import javax.persistence.*;

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
