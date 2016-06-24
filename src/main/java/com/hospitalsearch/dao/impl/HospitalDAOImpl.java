package com.hospitalsearch.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hospitalsearch.dao.HospitalDAO;
import com.hospitalsearch.dto.Bounds;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.util.HospitalFilterDTO;
import com.hospitalsearch.util.Page;
import com.hospitalsearch.util.comparators.ComparatorUtil;


/**
 *
 * @author Pavlo Kuz edited by Oleksandr Mukonin,Pavlo Kuz and others
 */
@Repository
public class HospitalDAOImpl extends GenericDAOImpl<Hospital, Long> implements HospitalDAO {

    @Autowired
    public HospitalDAOImpl(SessionFactory sessionFactory) {
        this.setSessionFactory(sessionFactory);
    }

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

    @Override
    public List<Hospital> filterHospitalsByAddress(HospitalFilterDTO filterInfo) {
        Criteria criteria = this.getSessionFactory()
                .getCurrentSession()
                .createCriteria(Hospital.class).createAlias("departments", "dep");
        Criterion nameCriterion = Restrictions.like("name", filterInfo.getName(), MatchMode.ANYWHERE);
        Criterion countryCriterion = Restrictions.like("dep.name", filterInfo.getCountry(), MatchMode.ANYWHERE);
        Criterion cityCriterion = Restrictions.like("address.city", filterInfo.getCity(), MatchMode.ANYWHERE);

        return criteria.add(Restrictions.and(nameCriterion, countryCriterion, cityCriterion)).list();
    }

    public static final String[] HOSPITAL_SEARCH_PROJECTION = new String[]{"name", "address.city", "address.street", "address.building", "departments.name"};

    @Override
    public Page<Hospital> advancedHospitalSearch(String args,int pageSize,int page,boolean sortAsc) throws ParseException, InterruptedException {
        //make index of all data stored by administrator
    	FullTextSession session = Search.getFullTextSession(this.getSessionFactory().openSession());
        session.createIndexer(Hospital.class).startAndWait();
        session.close();
    	
    	int pageCount = 0; 
        boolean paginated = false;
        int resultListCount =0;
        
        session = Search.getFullTextSession(this.getSessionFactory().getCurrentSession());
    	QueryBuilder builder = session.getSearchFactory().buildQueryBuilder().forEntity(Hospital.class).get();
        Query query = builder.keyword().fuzzy().onFields(HOSPITAL_SEARCH_PROJECTION).matching(args).createQuery();
           
        FullTextQuery fullTextQuery = session.createFullTextQuery(query, Hospital.class).setCriteriaQuery(session.createCriteria(Hospital.class));
        resultListCount = fullTextQuery.getResultSize();
        
        if (pageSize < resultListCount) {
            pageCount = resultListCount / pageSize;
            if(resultListCount % pageSize > 0) pageCount++;
            paginated = true;
        } else {
            pageCount = 1;
            paginated = false;
        }
        fullTextQuery.setFirstResult((pageSize*(page-1))).setMaxResults(pageSize);
        fullTextQuery.setSort(new Sort(new SortField("name", Type.STRING_VAL)));
        
        Logger.getLogger(Page.class.getName()).log(java.util.logging.Level.ALL, fullTextQuery.toString());
        List<Hospital> result = fullTextQuery.list();
        session.close();
        
        return new Page<Hospital>(result,paginated,pageCount,pageSize,resultListCount,sortAsc,ComparatorUtil.HOSPITAL_COMPARATOR);
    }

}
