package com.hospitalsearch.util;

import java.time.LocalDateTime;

import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.service.UserDetailService;

public class FeedbackDTO {
	private String userEmail;
	private Long doctorId;
	private String message;


	public FeedbackDTO() {
		// TODO Auto-generated constructor stub
	}

	
	public FeedbackDTO(String userEmail, Long doctorId, String message) {
		super();
		this.userEmail = userEmail;
		this.doctorId = doctorId;
		this.message = message;
		
		
	}


	public Feedback buildFeedback(User consumer,User producer){
		Feedback feedback = new Feedback();
		feedback.setDate(LocalDateTime.now());
		feedback.setMessage(message);
		feedback.setConsumer(consumer);
		feedback.setProducer(producer);
		return feedback;
	}




	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
