package com.hospitalsearch.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class PatientCard {
	   @Id
	   @GeneratedValue
	   private Long id;
	   @OneToOne
	   private User patient;
	   
	   
	   
	   
}
