package com.hospitalsearch.service;

import com.hospitalsearch.dto.Bounds;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.util.HospitalFilterDTO;
import com.hospitalsearch.util.Page;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 * @author Pavlo Kuz
 * edited by Oleksandr Mukonin
 */
@Component
@Transactional
public interface HospitalService {
    void save(Hospital newHospital);
    void delete(Hospital hospital);
    void update(Hospital updatedHospital);
    @Transactional(readOnly=true,propagation= Propagation.SUPPORTS)
    Hospital getById(Long id);
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<Hospital> getAll();
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<Hospital> getAllByBounds(Bounds bounds);	
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    public List<Hospital> filterHospitalsByAddress(HospitalFilterDTO filterInfo);
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    public Page<Hospital> advancedHospitalSearch(String args) throws ParseException, InterruptedException;
}