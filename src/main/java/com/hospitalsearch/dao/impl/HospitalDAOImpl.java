package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.HospitalDAO;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.util.Bounds;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Pavlo Kuz
 * edited by Oleksandr Mukonin
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
}
