package com.hospitalsearch.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.hospitalsearch.dao.GenericDAO;


abstract public class GenericDAOImpl<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDAO<T, PK> {
	public Class<T> type;

	
	@SuppressWarnings("unchecked")
	public GenericDAOImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		this.type = (Class<T>) pt.getActualTypeArguments()[0];		
	}
	
	@Override
	public void save(T instance) {
		 getHibernateTemplate().save(instance);
	}

	@Override
	public void delete(T instance) {
		 getHibernateTemplate().delete(instance);
	}

	@Override
	public void update(T instance) {
		 getHibernateTemplate().update(instance);
	}

	@Override
	public T getById(PK id) {
		return  getHibernateTemplate().get(type, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		return (List<T>)  getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(type));
	}

}
