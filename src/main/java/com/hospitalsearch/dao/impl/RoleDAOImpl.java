package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.RoleDAO;
import com.hospitalsearch.entity.Role;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by andrew on 11.05.16.
 */
@Repository("roleDAO")
public class RoleDAOImpl extends GenericDAOImpl<Role, Long> implements RoleDAO {

    @Autowired
    public RoleDAOImpl(SessionFactory factory) {
        super();
        this.setSessionFactory(factory);
    }

    @Override
    public Role getByType(String type) {
        Criteria crit = this.currentSession().createCriteria(Role.class);
        crit.add(Restrictions.eq("type", type));
        return (Role) crit.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Role> getAll() {
        Criteria crit = this.currentSession().createCriteria(Role.class);
        crit.addOrder(Order.asc("type"));
        return (List<Role>) crit.list();
    }

       /* @Override
    public Role getByKey(Long id) {
        return super.getByKey(id);
    }

    @Override
    public Role getByType(String type) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("type", type));
        return (Role) crit.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Role> getAll() {
        Criteria crit = createEntityCriteria();
        crit.addOrder(Order.asc("type"));
        return (List<Role>) crit.list();
    }*/

}
