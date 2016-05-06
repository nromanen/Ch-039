package com.hospitalsearch.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Hospital implements Serializable {
	
	private static final long serialVersionUID = 8874100249877394394L;
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private Double latitude;
	private Double longitude;
	private String address; 
	private String description; 
	private String placeId;
	
	public Hospital() {
		// TODO Auto-generated constructor stub
	}

	public Hospital(Long id, String title, Double latitude, Double longitude, String address, String description,
			String placeId) {
		super();
		this.id = id;
		this.title = title;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.description = description;
		this.placeId = placeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
		
	
}
