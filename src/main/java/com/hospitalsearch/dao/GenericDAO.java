package com.hospitalsearch.dao;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;


@Component
public interface GenericDAO<T,PK extends Serializable> {
	void save(T instance);
	void delete(T instance);
	void update(T instance);
	T getById(PK id);
	List<T> getAll();
}
