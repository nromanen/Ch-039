package com.hospitalsearch.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="users")
@PrimaryKeyJoinColumn(name = "id")
public class User extends UserDetail implements Serializable {
	private String email;
	private String password;
	private Boolean enabled;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="ROLE_USERS", joinColumns=@JoinColumn(name="ROLE_ID"),inverseJoinColumns=@JoinColumn(name="USERS_ID"))
	private List<Role> roles;

	public User() {
		
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



	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
