package com.hospitalsearch.entity;

import com.hospitalsearch.service.RoleService;
import com.hospitalsearch.service.impl.RoleServiceImpl;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role implements Serializable {

	private static final long serialVersionUID = 6882428284905479070L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "type", unique = true, nullable = false)
	private String type;

	@ManyToMany(mappedBy = "userRoles")
	private Set<User> user = new HashSet<>();

	public Role() {}

	public Role(String type) {
		super();
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<User> getUser() {
		return user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Role{" +
				"type='" + type + '\'' +
				", id=" + id +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Role role = (Role) o;
		return !(id != null ? !id.equals(role.id) : role.id != null);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
