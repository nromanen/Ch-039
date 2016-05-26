package com.hospitalsearch.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospitalsearch.dao.HospitalDAO;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.util.Bounds;
import com.hospitalsearch.util.HospitalFilterDTO;

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
		Query query = this.currentSession().getNamedQuery(Hospital.GET_LIST_BY_BOUNDS)
				.setDouble("nelat", bounds.getNorthEastLat())
				.setDouble("swlat", bounds.getSouthWestLat())
				.setDouble("nelng", bounds.getNorthEastLon())
				.setDouble("swlng", bounds.getSouthWestLon());
		return query.list();
	}

	public void deleteById(long id) {
		Query query = this.currentSession().getNamedQuery(Hospital.DELETE_HOSPITAL_BY_ID);
		query.setLong("id", id)
		.executeUpdate();		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Hospital> filterHospitalsByAddress(HospitalFilterDTO filterInfo) {
		Criteria criteria = this.getSessionFactory()
				.getCurrentSession()
				.createCriteria(Hospital.class);
		Criterion nameCriterion = Restrictions.like("name", filterInfo.getName(),MatchMode.ANYWHERE);
		Criterion countryCriterion = Restrictions.like("address.country", filterInfo.getCountry(),MatchMode.ANYWHERE);
		Criterion cityCriterion = Restrictions.like("address.city", filterInfo.getCity(),MatchMode.ANYWHERE);
		
		return criteria.add(Restrictions.and(nameCriterion, countryCriterion,cityCriterion)).list();
	}
}
