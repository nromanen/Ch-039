package com.hospitalsearch.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;

@Entity
@Table(name = "doctorinfo")
public class DoctorInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctorinfo_gen")
    @SequenceGenerator(name = "doctorinfo_gen", sequenceName = "doctorinfo_id_seq", initialValue = 1, allocationSize = 1)

    private Long id;
    @Field
    private String specialization;

    @OneToOne
    @JoinColumn(name="userdetails_id")
    private UserDetail userDetails;
    
    @ManyToMany(mappedBy = "doctors")
    @ContainedIn
    private List<Department> departments;

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
   
}