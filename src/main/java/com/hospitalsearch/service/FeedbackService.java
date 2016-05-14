package com.hospitalsearch.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hospitalsearch.entity.Feedback;
@Transactional
public interface FeedbackService {
	void save(Feedback newFeedback);
	void delete(Feedback feedback);
	void update(Feedback updatedFeedback);
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	Feedback getById(Long id);
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	List<Feedback> getAll();
}
