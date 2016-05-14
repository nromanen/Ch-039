package com.hospitalsearch.service;

import com.hospitalsearch.entity.Department;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DepartmentService {
    void save(Department newDepartment);
    void delete(Department department);
    void update(Department updatedDepartment);
    @Transactional(readOnly=true,propagation= Propagation.SUPPORTS)
    Department getById(Long id);
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<Department> getAll();
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    public List<Department> findByHospitalId(Long id);
}

