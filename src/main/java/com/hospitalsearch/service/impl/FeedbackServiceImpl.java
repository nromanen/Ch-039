package com.hospitalsearch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospitalsearch.dao.FeedbackDAO;
import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.FeedbackService;
import com.hospitalsearch.util.Page;

@Service
public class FeedbackServiceImpl implements FeedbackService{

	@Autowired
	private FeedbackDAO dao;
	
	@Override
	public void save(Feedback newFeedback) {
		dao.save(newFeedback);
	}	
	@Override
	public void delete(Feedback feedback) {
		dao.delete(feedback);
	}
	@Override
	public void update(Feedback updatedFeedback) {
		dao.update(updatedFeedback);	
	}	
	@Override
	public Feedback getById(Long id) {
		return dao.getById(id);
	}
	@Override
	public List<Feedback> getAll() {
		
		return dao.getAll(); 
	}
	@Override
	public List<Feedback> getByDoctorId(Long id) {
		return dao.getByDoctorId(id);
	}
	@Override
	public User getByUserEmail(String email) {
		return dao.getByUserEmail(email);
	}
	@Override
	public Feedback getByProducer(User user) {
		return dao.getByProducer(user);
	}
	@Override
	public List<Feedback> getFeedbacksByUserEmailAndDoctorId(String email,int id) {
		return dao.getFeedbacksByUserEmailAndDoctorId(email, id);
	}
	@Override
	public List<Feedback> filterByEmail(String email, String sender) {
		return dao.filterByEmail(email, sender);
	}
	@Override
	public List<Feedback> filterByMessage(String partOfMessage) throws InterruptedException {
		return dao.filterByMessage(partOfMessage);
	}
	@Override
	public Page<Feedback> getFeedbacksByPage(int page) {
		return dao.getFeedbacksByPage(page);
	}
	

}
