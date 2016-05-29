package com.hospitalsearch.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author Oleksandr Mukonin
 * edited Pavel Kuz
 *
 */
@Entity
@Table(name = "hospital")

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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hospital_gen")
	@SequenceGenerator(name = "hospital_gen", sequenceName = "hospital_id_seq")
	private Long id;

	@NotEmpty
	@Size(min = 10, max = 50)
	@Column(name = "NAME", nullable = false)
	private String name;

	@NotNull
	@Min(-90)
	@Max(90)
	@Column(name = "LATITUDE", nullable = false)
	private Double latitude;

	@NotNull
	@Min(-180)
	@Max(180)
	@Column(name = "LONGITUDE", nullable = false)
	private Double longitude;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="city",column=@Column(name="city")),
		@AttributeOverride(name="country",column=@Column(name="country")),
		@AttributeOverride(name="street",column=@Column(name="street")),
		@AttributeOverride(name="number",column=@Column(name="number"))
	})
	private HospitalAddress address;

	@Size(max = 150)
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

	public HospitalAddress getAddress() {
		return address;
	}

	public void setAddress(HospitalAddress address) {
		this.address = address;
	}	
	
}
