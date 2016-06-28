package com.hospitalsearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by tsapy on 24.06.2016.
 */
@Entity
@Table(name = "workscheduler")
public class WorkScheduler {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "verification_token_gen")
    @SequenceGenerator(name = "verification_token_gen", sequenceName = "verification_token_gen_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "workscheduler", columnDefinition = "text")
    private String workScheduler;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name="doctorinfo_id")
    private DoctorInfo doctorInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkScheduler() {
        return workScheduler;
    }

    public void setWorkScheduler(String workScheduler) {
        this.workScheduler = workScheduler;
    }

    public DoctorInfo getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(DoctorInfo doctorInfo) {
        this.doctorInfo = doctorInfo;
    }

    @Override
    public String toString() {
        return "WorkScheduler{" +
                "id=" + id +
                ", workScheduler='" + workScheduler + '\'' +
                '}';
    }
}


