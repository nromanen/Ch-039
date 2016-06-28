package com.hospitalsearch.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.util.Page;
@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
public interface FeedbackService {
	@Transactional(propagation=Propagation.REQUIRED)
	void save(Feedback newFeedback);
	
	@Transactional(propagation=Propagation.REQUIRED)
	void delete(Feedback feedback);
	
	@Transactional(propagation=Propagation.REQUIRED)
	void update(Feedback updatedFeedback);
	
	
	Feedback getById(Long id);
	
	List<Feedback> getAll();
	
	public List<Feedback> getByDoctorId(Long id);
	
	public User getByUserEmail(String email);
	
	public Feedback getByProducer(User user);
	
	public List<Feedback> getFeedbacksByUserEmail(String email);
	
	public List<Feedback> filterByEmail(String email, String sender);
	
	public Page<Feedback> getFeedbacksByPage(int page);
	
	public List<Feedback> filterByMessage(String partOfMessage) throws InterruptedException;
}
