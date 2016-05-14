package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.DepartmentDAO;
import com.hospitalsearch.entity.Department;
import com.hospitalsearch.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by deplague on 5/11/16.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentDAO dao;

    @Override
    public void save(Department newDepartment) {
        dao.save(newDepartment);
    }

    @Override
    public void delete(Department department) {
        dao.delete(department);
    }

    @Override
    public void update(Department updatedDepartment) {
        dao.update(updatedDepartment);
    }

    @Override
    public Department getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public List<Department> getAll() {
        return dao.getAll();
    }

    @Override
    public List<Department> findByHospitalId(Long id) {
        return dao.findByHospitalId(id);
    }
}
