package com.hospitalsearch.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.entity.User;

@Component
public interface FeedbackDAO extends GenericDAO<Feedback, Long>{
	public List<Feedback> getByDoctorId(Long id);
	public User getByUserEmail(String email);
	public Feedback getByProducer(User user);
}
