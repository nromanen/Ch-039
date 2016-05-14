package com.hospitalsearch.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
@PrimaryKeyJoinColumn(name = "id")
public class User extends UserDetail implements Serializable {
	private String email;
	private String password;
	private Boolean enabled;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="ROLE_USERS", joinColumns=@JoinColumn(name="USERS_ID"),inverseJoinColumns=@JoinColumn(name="ROLE_ID"))
	@Fetch(FetchMode.SELECT)
	private Set<Role> userRoles = new HashSet<Role>();
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

	public Set<Role> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<Role> userRoles) {
		this.userRoles = userRoles;
	}

	@Override
	public String toString() {
		return "User{" +
				"email='" + email + '\'' +
				", password='" + password + '\'' +
				", enabled=" + enabled +
				", userRoles=" + userRoles +
				'}';
	}
}
