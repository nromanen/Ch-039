package com.hospitalsearch.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
public class DoctorInfo implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    
    private String specialization;

    @OneToOne
    private UserDetail userDetails;
    
    @ManyToMany(mappedBy = "doctors")
    private List<Department> departments;

    @OneToMany(mappedBy = "doctorInfo")
    private List<WorkInterval> workIntervals;

    public DoctorInfo() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<Department> getDepartments() {
        return departments;
    }
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

	public UserDetail getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetail userDetails) {
		this.userDetails = userDetails;
	}

    public List<WorkInterval> getWorkIntervals() {
        return workIntervals;
    }

    public void setWorkIntervals(List<WorkInterval> workIntervals) {
        this.workIntervals = workIntervals;
    }
    
}