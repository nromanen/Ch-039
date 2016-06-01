package com.hospitalsearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hospitalsearch.util.CustomLocalDateTimeSerializer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
public class Appointment implements Serializable{

	@Id
	@GeneratedValue
	private Long id;

	@JsonIgnore
	@ManyToOne
	private UserDetail userDetail;

	@JsonIgnore
	@ManyToOne
	private DoctorInfo doctorInfo;


	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	private LocalDateTime start_date;
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	private LocalDateTime end_date;
	private String text;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDateTime start_date) {
		this.start_date = start_date;
	}

	public LocalDateTime getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDateTime end_date) {
		this.end_date = end_date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public DoctorInfo getDoctorInfo() {
		return doctorInfo;
	}

	public void setDoctorInfo(DoctorInfo doctorInfo) {
		this.doctorInfo = doctorInfo;
	}


	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Appointment{");
		sb.append("id=").append(id);
		sb.append(", start_date=").append(start_date);
		sb.append(", end_date=").append(end_date);
		sb.append(", text='").append(text).append('\'');
		sb.append('}');
		return sb.toString();
	}


}
