package com.hospitalsearch.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.entity.User;
@Transactional
public interface FeedbackService {
	void save(Feedback newFeedback);
	void delete(Feedback feedback);
	void update(Feedback updatedFeedback);
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	Feedback getById(Long id);
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	List<Feedback> getAll();
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<Feedback> getByDoctorId(Long id);
	public User getByUserEmail(String email);
	public Feedback getByProducer(User user);
}
