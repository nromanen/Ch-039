package com.hospitalsearch.dao.impl;


import java.util.List;
import java.util.logging.Level;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospitalsearch.dao.HospitalDAO;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.util.Bounds;
import com.hospitalsearch.util.HospitalFilterDTO;
import com.sun.istack.internal.logging.Logger;

/**
 * 
 * @author Pavlo Kuz
 * edited by Oleksandr Mukonin,Pavlo Kuz and other
 */
@Repository
public class HospitalDAOImpl extends  GenericDAOImpl<Hospital,Long> implements HospitalDAO{

	@Autowired
	public HospitalDAOImpl(SessionFactory sessionFactory){
		this.setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public List<Hospital> getAllByBounds(Bounds bounds) {
		org.hibernate.Query query = this.currentSession().getNamedQuery(Hospital.GET_LIST_BY_BOUNDS)
				.setDouble("nelat", bounds.getNorthEastLat())
				.setDouble("swlat", bounds.getSouthWestLat())
				.setDouble("nelng", bounds.getNorthEastLon())
				.setDouble("swlng", bounds.getSouthWestLon());
		return query.list();
	}

	public void deleteById(long id) {
		org.hibernate.Query query = this.currentSession().getNamedQuery(Hospital.DELETE_HOSPITAL_BY_ID);
		query.setLong("id", id)
		.executeUpdate();		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Hospital> filterHospitalsByAddress(HospitalFilterDTO filterInfo) {
		Criteria criteria = this.getSessionFactory()
				.getCurrentSession()
				.createCriteria(Hospital.class).createAlias("departments", "dep");
		Criterion nameCriterion = Restrictions.like("name", filterInfo.getName(),MatchMode.ANYWHERE);
		Criterion countryCriterion = Restrictions.like("dep.name", filterInfo.getCountry(),MatchMode.ANYWHERE);
		Criterion cityCriterion = Restrictions.like("address.city", filterInfo.getCity(),MatchMode.ANYWHERE);

		return criteria.add(Restrictions.and(nameCriterion, countryCriterion,cityCriterion)).list();
	}

	public static final String [] HOSPITAL_PROJECTION= new String[]{"name","address.city","address.street","address.building"};
	@Override
	public List<Hospital> advancedHospitalSearch(String args) throws ParseException, InterruptedException {
        FullTextSession session = Search.getFullTextSession(this.getSessionFactory().getCurrentSession());
		session.beginTransaction();
		session.createIndexer(Hospital.class).startAndWait();
		
        QueryBuilder builder = session.getSearchFactory().buildQueryBuilder().forEntity(Hospital.class).get();
        Query query = builder.keyword().fuzzy().onFields(HOSPITAL_PROJECTION).matching(args).createQuery();
        Logger.getLogger(HospitalDAOImpl.class).log(Level.INFO, query.toString());
		List<Hospital> hospitals = session.createFullTextQuery(query
				,Hospital.class).list();
		session.getTransaction().commit();
		return hospitals;
	}
}
