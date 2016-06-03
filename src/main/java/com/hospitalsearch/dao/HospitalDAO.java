package com.hospitalsearch.dao;

import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;

import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.util.Bounds;
import com.hospitalsearch.util.HospitalFilterDTO;

/**
 * 
 * @author Oleksandr Mukonin
 *
 */

public interface HospitalDAO extends  GenericDAO<Hospital,Long> {
	
	List<Hospital> getAllByBounds(Bounds bounds);
	
	void deleteById(long id);
	
	List<Hospital> filterHospitalsByAddress(HospitalFilterDTO filterInfo);
	List<Hospital> advancedHospitalSearch(String args) throws ParseException, InterruptedException ;

}
