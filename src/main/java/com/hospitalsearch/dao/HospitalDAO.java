package com.hospitalsearch.dao;

import java.util.List;

import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.util.Bounds;

/**
 * 
 * @author Oleksandr Mukonin
 *
 */

public interface HospitalDAO extends  GenericDAO<Hospital,Long> {
	
	List<Hospital> getAllByBounds(Bounds bounds);
	
	void deleteById(long id);

}
