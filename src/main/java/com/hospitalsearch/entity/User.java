package com.hospitalsearch.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="users")

@NamedQueries({
	@NamedQuery(name = "SELECT_BY_ROLE", query = "SELECT u FROM User u JOIN  u.userRoles r WHERE r.id = :id"),
	@NamedQuery(name = "SELECT_ALL_ENABLED_USERS", query = "SELECT u FROM User u WHERE u.enabled = true"),
		@NamedQuery(name = "SELECT_ALL_DISABLED_USERS", query = "SELECT u FROM User u WHERE u.enabled = false"),
})
	
public class User implements Serializable, Comparable<User> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_gen")
	@SequenceGenerator(name = "users_gen", sequenceName = "users_id_seq")
	private Long id;

	@Email
	@NotEmpty
	@Column(unique = true, nullable = false)
	private String email;

	@JsonIgnore
	@NotNull
	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private Boolean enabled= true;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="ROLE_USERS", joinColumns = @JoinColumn(name="USERS_ID"), inverseJoinColumns = @JoinColumn(name="ROLE_ID"))
	@Fetch(FetchMode.SELECT)
	private Set<Role> userRoles = new HashSet<>();

	@OneToOne(cascade= CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private UserDetail userDetails;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDetail getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetail userDetails) {
		this.userDetails = userDetails;
	}


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", userRoles=" + userRoles +
                ", userDetails=" + userDetails +
                '}';
    }

	@Override
	public int compareTo(User o) {
		return this.getId().compareTo(o.getId());
	}
}
