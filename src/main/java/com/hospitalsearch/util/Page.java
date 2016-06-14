/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.util;

import java.util.List;
import java.util.logging.Logger;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.hospitalsearch.entity.Hospital;


/**
 *
 * @author kpaul
 * @param <T> entity, which can be used for making pagination
 */
public final class Page<T>{

    private final String query;
    private final SessionFactory sessionFactory;
    private int resultListCount;
    private int pageSize = 3;
    private int pageCount;
    private boolean paginated;
    private String[] projection;
    private String sortType ="Asc";
    private Class<T> clazz;

    
    public Page(SessionFactory sessionFactory,String query,String[] projection) {
        this.sessionFactory = sessionFactory;
        this.query =query;
        this.projection = projection;
        this.clazz = (Class<T>) getClass().getTypeParameters()[0].getGenericDeclaration();
    }

    public void setSortType(String sortType) {
		this.sortType = sortType;
	}
    
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public int getResultListCount() {
        return resultListCount;
    }

    
    public Integer getPageCount() {
        return pageCount;
    }

    public List<T> getPageList(Integer page) {
    	FullTextSession session = Search.getFullTextSession(this.sessionFactory.openSession());
    	Order order = this.sortType.startsWith("Ascen")?Order.asc("name"):Order.desc("name");
        
    	QueryBuilder builder = session.getSearchFactory().buildQueryBuilder().forEntity(Hospital.class).get();
        Query query = builder.keyword().fuzzy().onFields(projection).matching(this.query).createQuery();
           
        FullTextQuery fullTextQuery = session.createFullTextQuery(query, Hospital.class).setCriteriaQuery(session.createCriteria(Hospital.class).addOrder(order));
        
        this.resultListCount = fullTextQuery.getResultSize();
        
        if (this.pageSize < this.resultListCount) {
            this.pageCount = this.resultListCount / this.pageSize;
            
            this.paginated = true;
        } else {
            this.pageCount = 1;
            this.paginated = false;
        }
        fullTextQuery.setFirstResult((pageSize*(page-1))).setMaxResults(pageSize);
        fullTextQuery.setSort(new Sort(new SortField("name", Type.STRING_VAL)));
        
        Logger.getLogger(Page.class.getName()).log(java.util.logging.Level.ALL, fullTextQuery.toString());
        List<T> result = (List<T>) fullTextQuery.list();
        session.close();
        return result;
    }

    public boolean isPaginated() {
        return paginated;
    }
        

    public String getSortType() {
		return sortType;
	}
}
