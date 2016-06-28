package com.hospitalsearch.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.util.Page;

@Component
public interface FeedbackDAO extends GenericDAO<Feedback, Long>{
	public Page<Feedback> getFeedbacksByPage(int page);
	public List<Feedback> getByDoctorId(Long id);
	public User getByUserEmail(String email);
	public Feedback getByProducer(User user);
	public List<Feedback> getFeedbacksByUserEmail(String email);
	public List<Feedback> filterByEmail(String email,String sender);
	public List<Feedback> filterByMessage(String partOfMessage) throws InterruptedException;
}
