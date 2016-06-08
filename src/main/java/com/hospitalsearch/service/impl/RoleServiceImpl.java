package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.RoleDAO;
import com.hospitalsearch.entity.Role;
import com.hospitalsearch.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @author Andrew Jasinskiy
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDAO dao;

    @Override
    public List<Role> getAll() {
        return dao.getAll();
    }

    @Override
    public Role getByType(String type) {
        return dao.getByType(type);
    }

    @Override
    public Role getById(long id) {
        return dao.getById(id);
    }

}
