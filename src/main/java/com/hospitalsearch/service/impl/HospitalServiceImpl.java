package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.HospitalDAO;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.service.HospitalService;
import com.hospitalsearch.util.Bounds;
import com.hospitalsearch.util.HospitalFilterDTO;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @author Pavlo Kuz
 * edited by Oleksandr Mukonin
 */
@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalDAO dao;

    @Override
    public void save(Hospital newHospital) {
        dao.save(newHospital);
    }

    @Override
    public void delete(Hospital hospital) {
        dao.delete(hospital);
    }

    @Override
    public void update(Hospital updatedHospital) {
        dao.update(updatedHospital);
    }

    @Override
    public Hospital getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public List<Hospital> getAll() {
        return dao.getAll();
    }

	@Override
	public void deleteById(long id) {
		dao.deleteById(id);
	}	

	@Override
	public List<Hospital> getAllByBounds(Bounds bounds) {
		return dao.getAllByBounds(bounds);		
	}

	@Override
	public List<Hospital> filterHospitalsByAddress(HospitalFilterDTO filterInfo) {
		return dao.filterHospitalsByAddress(filterInfo);
	}

	@Override
	public List<Hospital> advancedHospitalSearch(String args) throws ParseException, InterruptedException {
		// TODO Auto-generated method stub
		return dao.advancedHospitalSearch(args);
	}
}
