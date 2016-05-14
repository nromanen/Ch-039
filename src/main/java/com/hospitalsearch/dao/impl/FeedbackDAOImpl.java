package com.hospitalsearch.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospitalsearch.dao.FeedbackDAO;
import com.hospitalsearch.entity.Feedback;

@Repository
public class FeedbackDAOImpl extends GenericDAOImpl<Feedback, Long> implements FeedbackDAO {
 
	@Autowired
	public FeedbackDAOImpl(SessionFactory factory) {
		this.setSessionFactory(factory);
	}

}
