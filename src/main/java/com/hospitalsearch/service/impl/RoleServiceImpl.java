package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.RoleDAO;
import com.hospitalsearch.entity.Role;
import com.hospitalsearch.service.RoleService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Andrew Jasinskiy
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final Logger logger = LogManager.getLogger(RoleServiceImpl.class);

    @Autowired
    RoleDAO dao;

    @Override
    public List<Role> getAll() {
        List<Role> roles = new ArrayList<>();
        try {
            logger.info("Gel All Roles");
            roles = dao.getAll();
            return roles;
        } catch (Exception e) {
            logger.error("Error getting all users", e);
        }
        return roles;
    }

    @Override
    public Role getByType(String type) {
        Role role = new Role();
        try {
            logger.info("Gel Role by type " + type);
            role = dao.getByType(type);
            return role;
        } catch (Exception e) {
            logger.error("Error Role by type " + type, e);
        }
        return role;
    }

    @Override
    public Role getById(long id) {
        Role role = new Role();
        try {
            logger.info("Gel Role by id " + id);
            role = dao.getById(id);
            return role;
        } catch (Exception e) {
            logger.error("Error Role by id " + id, e);
        }
        return role;
    }
}
