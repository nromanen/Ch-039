package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.RoleDAO;
import com.hospitalsearch.entity.Role;
import com.hospitalsearch.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by andrew on 11.05.16.
 */

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDAO dao;

    @Override
    public List<Role> getAll() {
        return dao.getAll();
    }

    @Override
    public Role getByType(String role) {
        return dao.getByType(role);
    }

    @Override
    public Role getById(long id) {
        return dao.getById(id);
    }

}
