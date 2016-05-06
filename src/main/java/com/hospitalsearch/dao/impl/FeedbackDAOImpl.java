package com.hospitalsearch.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hospitalsearch.dao.FeedbackDAO;
import com.hospitalsearch.entities.Feedback;

@Repository
@Transactional
public class FeedbackDAOImpl extends HibernateDaoSupport implements FeedbackDAO{
	
	@Autowired
	public FeedbackDAOImpl(SessionFactory factory) {
		this.setSessionFactory(factory);
	}

	@Override
	public void add(Feedback newFeedback) {
		getHibernateTemplate().save(newFeedback);
	}

	@Override
	public void remove(Feedback feedback) {
		getHibernateTemplate().delete(feedback);
	}

	@Override
	public void update(Feedback updatedFeedback) {
		getHibernateTemplate().update(updatedFeedback);
	}

	@Transactional(readOnly=true)
	@Override
	public Feedback getById(Long id) {
		return getHibernateTemplate().get(Feedback.class, id);
	}

	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	@Override
	public List<Feedback> getAll() {
		return (List<Feedback>)getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(Feedback.class));
	}
}
