package com.hospitalsearch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hospitalsearch.util.CustomLocalDateTimeSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class WorkInterval implements Serializable{

	@Id
	@GeneratedValue
	private Long id;

	@JsonIgnore
	@ManyToOne
	private DoctorInfo doctorInfo;
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
//	@Column(name = "start_date", nullable = false)
	private LocalDateTime start_date;
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
//	@Column(name = "end_date", nullable = false)
	private LocalDateTime end_date;
//	@Column(name = "text", nullable = false)
	private String text;

	public WorkInterval() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DoctorInfo getDoctorInfo() {
		return doctorInfo;
	}

	public void setDoctorInfo(DoctorInfo doctorInfo) {
		this.doctorInfo = doctorInfo;
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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("WorkInterval{");
		sb.append("id=").append(id);
		sb.append(", start_date=").append(start_date);
		sb.append(", end_date=").append(end_date);
		sb.append(", text='").append(text).append('\'');
		sb.append('}');
		return sb.toString();
	}


}
