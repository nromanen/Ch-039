package com.hospitalsearch.entity;

import com.hospitalsearch.service.annotation.OneDay;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
public class CardItem implements Serializable {
	
	
	private static final long serialVersionUID = -1407036211724471026L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneDay(message = " Edit time is over")
    private LocalDate date;
    @ManyToOne
    PatientCard patientCard;
    @NotNull
    @Size(min = 5, message = "min size = 5")
    private String result;
    @NotNull
    @Size(min = 5, message = "min size = 5")
    private String prescription;
    @NotNull
    @Size(min = 5, message = "min size = 5")
    private String complaint;
    @ManyToOne
    private User doctor;

    public CardItem() {

    }

    public CardItem(LocalDate date, PatientCard patientCard, String result, String prescription, User doctor) {
        this.date = date;
        this.patientCard = patientCard;
        this.result = result;
        this.prescription = prescription;
        this.doctor = doctor;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    @Override
    public String toString() {
        return "CardItem{" +
                "date=" + date +
                ", result='" + result + '\'' +
                ", prescription='" + prescription + '\'' +
                ", doctor=" + doctor +
                '}';
    }
	
}
