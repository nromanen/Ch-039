package com.hospitalsearch.entity;

import com.hospitalsearch.service.annotation.OneDay;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "carditem")
public class CardItem implements Serializable {


    private static final long serialVersionUID = -1407036211724471026L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carditem_gen")
    @SequenceGenerator(name = "carditem_gen", sequenceName = "carditem_id_seq")
    private Long id;
    @OneDay(message = " Edit time is over")
    private Timestamp date;
    @ManyToOne
    @Fetch(FetchMode.JOIN)
    PatientCard patientCard;
    @NotNull
    @Column( name = "RESULT", columnDefinition = "TEXT")
    @Size(min = 5, max = 1000, message = "size = 5 < 255")
    private String result;
    @NotNull
    @Column( name = "PRESCRIPTION", columnDefinition = "TEXT")
    @Size(min = 5, max = 1000, message = "size = 5 < 1000")
    private String prescription;
    @NotNull
    @Column( name = "COMPLAINT", columnDefinition = "TEXT")
    @Size(min = 5, max = 1000, message = "size = 5 < 1000")
    private String complaint;
    @ManyToOne
    private User doctor;

    public CardItem() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp  getDate() {
        return date;
    }

    public void setDate(Timestamp  date) {
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

}
