package com.hospitalsearch.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
@Table(name = "feedback")
@Indexed
public class Feedback{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_gen")
	@SequenceGenerator(name = "feedback_gen", sequenceName = "feedback_id_seq", initialValue = 1, allocationSize = 1)
	private Long id;
	@Field(analyze = Analyze.YES, analyzer = @Analyzer(definition = "ngram"))
	private String message;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private User producer;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private User consumer;
	
	private LocalDateTime date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getProducer() {
		
		return producer;
	}

	public void setProducer(User producer) {
		this.producer = producer;
	}

	public User getConsumer() {
		return consumer;
	}

	public void setConsumer(User consumer) {
		this.consumer = consumer;
	}

	public LocalDateTime getDate() {
		
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
}
