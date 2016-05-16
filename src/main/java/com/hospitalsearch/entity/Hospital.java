package com.hospitalsearch.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author Oleksandr Mukonin
 *
 */
@Entity
@Table(name = "HOSPITAL")

@NamedQueries
(
	{
		@NamedQuery(name = Hospital.DELETE_HOSPITAL_BY_ID, query = Hospital.DELETE_HOSPITAL_BY_ID_QUERY),
		@NamedQuery(name = Hospital.GET_LIST_BY_BOUNDS, query = Hospital.GET_LIST_BY_BOUNDS_QUERY)
	}
)
public class Hospital{
	
	static final String GET_LIST_BY_BOUNDS_QUERY = "from Hospital h where "
			+ "(latitude < :nelat) and "
			+ "(latitude > :swlat) and "
			+ "(longitude < :nelng) and "
			+ "(longitude > :swlng)";
	public static final String GET_LIST_BY_BOUNDS = "GET_LIST_BY_BOUNDS";
	
	static final String DELETE_HOSPITAL_BY_ID_QUERY = "DELETE Hospital WHERE id = :id";
	public static final String DELETE_HOSPITAL_BY_ID = "DELETE_HOSPITAL_BY_ID";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotEmpty
	@Column(name = "NAME", nullable = false)
	private String name;

	@NotNull
	@Column(name = "LATITUDE", nullable = false)
	private Double latitude;

	@NotNull
	@Column(name = "LONGITUDE", nullable = false)
	private Double longitude;
	
	@NotEmpty
	@Column(name = "ADDRESS", nullable = false)
	private String address; 
	
	@Column(name = "DESCRIPTION", nullable = false)
	private String description; 
	
	private String imagePath;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
