package com.hospitalsearch.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = 5468314444316440896L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String email;
	private String password;
	private Boolean enabled;
	@ManyToMany
	@JoinTable(name="ROLE_USER", joinColumns=@JoinColumn(name="ROLE_ID"),inverseJoinColumns=@JoinColumn(name="USER_ID"))
	private List<Role> roles;
	
	
	
	public User() {
		
	}


	public User(String email, String password, Boolean enabled, List<Role> roles) {
		super();
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}


	public List<Role> getRole() {
		return roles;
	}

	public void setRole(List<Role> roles) {
		this.roles = roles;
	}


}
