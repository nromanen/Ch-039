package com.hospitalsearch.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hospitalsearch.entities.Feedback;

@Component
public interface FeedbackDAO {
	void add(Feedback newFeedback);
	void remove(Feedback feedback);
	void update(Feedback updatedFeedback);
	Feedback getById(Long id);
	List<Feedback> getAll();
}
