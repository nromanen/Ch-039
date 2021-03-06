package com.hospitalsearch.dao;

import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;

import com.hospitalsearch.dto.Bounds;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.util.HospitalFilterDTO;
import com.hospitalsearch.util.Page;


/**
 *
 * @author Oleksandr Mukonin,Pavel Kuz
 *
 */
public interface HospitalDAO extends GenericDAO<Hospital, Long> {

    List<Hospital> getAllByBounds(Bounds bounds);

    List<Hospital> filterHospitalsByAddress(HospitalFilterDTO filterInfo);

    Page<Hospital> advancedHospitalSearch(String args) throws ParseException, InterruptedException;

}
