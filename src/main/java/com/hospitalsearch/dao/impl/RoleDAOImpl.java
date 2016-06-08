package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.RoleDAO;
import com.hospitalsearch.entity.Role;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Jasinskiy
 */
@Repository
public class RoleDAOImpl extends GenericDAOImpl<Role, Long> implements RoleDAO {

    private static Logger logger = LogManager.getLogger(RoleDAOImpl.class);

    @Autowired
    public RoleDAOImpl(SessionFactory factory) {
        super();
        this.setSessionFactory(factory);
    }

    @Override
    public Role getByType(String type) {
        Role role = new Role();
        try {
            logger.info("get role by type: " +type);
            Criteria criteria = this.currentSession().createCriteria(Role.class);
            criteria.add(Restrictions.eq("type", type));
            return role = (Role) criteria.uniqueResult();
        } catch (Exception e) {
            logger.error("Error get role by email: " + type + e);
        }
        return role;
    }

    @Override
    public List<Role> getAll() {
        List<Role> roles = new ArrayList<>();
        try {
            logger.info("get all roles");
            Criteria criteria = this.currentSession().createCriteria(Role.class);
            criteria.addOrder(Order.asc("type"));
            return roles = criteria.list();
        } catch (Exception e) {
            logger.error("Error get all roles " + e);
        }
        return roles;
    }
}
