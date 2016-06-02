package com.hospitalsearch.entity;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "doctorinfo")
public class DoctorInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctorinfo_gen")
    @SequenceGenerator(name = "doctorinfo_gen", sequenceName = "doctorinfo_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;
    
    private String specialization;

    @OneToOne
    @JoinColumn(name="userdetails_id")
    private UserDetail userDetails;
    
    @ManyToMany(mappedBy = "doctors")
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