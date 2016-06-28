package com.hospitalsearch.dao.impl;

import java.util.List;

import org.apache.lucene.search.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospitalsearch.dao.FeedbackDAO;
import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.util.Page;

@Repository
public class FeedbackDAOImpl extends GenericDAOImpl<Feedback, Long> implements FeedbackDAO {
	public static final int PAGE_SIZE = 10;
	@Autowired
	public FeedbackDAOImpl(SessionFactory factory) {
		this.setSessionFactory(factory);
	}

	@Override
	public List<Feedback> getByDoctorId(Long id) {
		return (List<Feedback>) getSessionFactory().getCurrentSession().createCriteria(Feedback.class).add(Restrictions.eq("consumer.id", id)).addOrder(Order.desc("date")).list();
	}

	@Override
	public User getByUserEmail(String email) {
		return (User) getSessionFactory().getCurrentSession().createCriteria(User.class).add(Restrictions.eq("email", email)).list().get(0);
	}

	@Override
	public Feedback getByProducer(User user) {
		return (Feedback) getSessionFactory().getCurrentSession().createCriteria(Feedback.class).add(Restrictions.eq("producer.id", user.getId())).list().get(0);
	}


	@Override
	public List<Feedback> getFeedbacksByUserEmailAndDoctorId(String email,int id) {
		return  getSessionFactory().getCurrentSession().createCriteria(Feedback.class)
				.createAlias("producer", "p")
				.createAlias("consumer", "d")
				.add(Restrictions.and(Restrictions.eq("p.email", email),Restrictions.eq("d.id", id) )).list();
	}

	@Override
	public List<Feedback> filterByEmail(String email, String sender) {
		return getSessionFactory().getCurrentSession().createCriteria(Feedback.class).createAlias(sender, "s").add(Restrictions.like(String.format("s.email", sender),email,MatchMode.ANYWHERE)).list();
	}

	@Override
	public List<Feedback> filterByMessage(String partOfMessage) throws InterruptedException {
		FullTextSession session = Search.getFullTextSession(getSessionFactory().getCurrentSession());
		session.createIndexer(Feedback.class).startAndWait();
		session = Search.getFullTextSession(getSessionFactory().getCurrentSession());
		
    	QueryBuilder builder = session.getSearchFactory().buildQueryBuilder().forEntity(Feedback.class).get();
        Query query = builder.keyword().fuzzy().onFields("message").matching(partOfMessage).createQuery();
           
        FullTextQuery fullTextQuery = session.createFullTextQuery(query, Feedback.class);
		return fullTextQuery.list();
	}

	@Override
	public Page<Feedback> getFeedbacksByPage(int page) {
	   	int pageCount = 0; 
        boolean paginated = false;
        int resultListCount =0;
        
        ScrollableResults scroll = this.currentSession().createCriteria(Feedback.class).scroll();
        scroll.last();
        resultListCount =  scroll.getRowNumber() +1;
        scroll.close();
        
        if (PAGE_SIZE < resultListCount) {
            pageCount = resultListCount / PAGE_SIZE;
            if(resultListCount % PAGE_SIZE > 0) pageCount++;
            paginated = true;
        } else {
            pageCount = 1;
            paginated = false;
        }
        
		return new Page<Feedback>(getSessionFactory().getCurrentSession()
				.createQuery("from Feedback f")
				.setFirstResult((PAGE_SIZE*(page-1)))
				.setMaxResults(PAGE_SIZE)
				.list(), paginated, pageCount, PAGE_SIZE, resultListCount, true, null);
	}
}
