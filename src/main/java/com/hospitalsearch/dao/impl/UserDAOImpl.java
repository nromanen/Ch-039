package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.dto.UserAdminDTO;
import com.hospitalsearch.dto.UserSearchDTO;
import com.hospitalsearch.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * @author Andrew Jasinskiy
 */
@Repository
public class UserDAOImpl extends GenericDAOImpl<User, Long> implements UserDAO {

    private final Logger logger = LogManager.getLogger(UserDAOImpl.class);

    @Autowired
    public UserDAOImpl(SessionFactory factory) {
        this.setSessionFactory(factory);
    }

    @Override
    public Boolean emailExists(String email) {
        return nonNull(getByEmail(email));
    }

    @Override
    public User getByEmail(String email) {
        try {
            logger.info("getUserByEmail email: " + email);
            Criteria criteria = this.currentSession().createCriteria(User.class);
            criteria.add(Restrictions.eq("email", email));
            return (User) criteria.uniqueResult();
        } catch (Exception e) {
            logger.error("Error get user by email: " + email + e);
            throw e;
        }
    }

    @Override
    public void changeStatus(long id) {
        User user = super.getById(id);
        user.setEnabled(!user.getEnabled());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllUser(UserAdminDTO userAdminDTO) {
        Criteria criteria = this.getSessionFactory()
                .getCurrentSession()
                .createCriteria(User.class);
        userAdminDTO.setTotalPage(getTotalPages(criteria, userAdminDTO.getPageSize()));
        getPagination(userAdminDTO, criteria);
        getAlias(criteria);
        getSort(userAdminDTO, criteria);
        return criteria.list();
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllEnabledUsers(UserAdminDTO userAdminDTO) {
        Criteria criteria = this.getSessionFactory()
                .getCurrentSession()
                .createCriteria(User.class, "user")
                .add(Restrictions.eq("user.enabled", true));
        userAdminDTO.setTotalPage(getTotalPages(criteria, userAdminDTO.getPageSize()));
        getPagination(userAdminDTO, criteria);
        getAlias(criteria);
        getSort(userAdminDTO, criteria);
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllDisabledUsers(UserAdminDTO userAdminDTO) {
        Criteria criteria = this.getSessionFactory()
                .getCurrentSession()
                .createCriteria(User.class, "user")
                .add(Restrictions.eq("user.enabled", false));
        userAdminDTO.setTotalPage(getTotalPages(criteria, userAdminDTO.getPageSize()));
        getPagination(userAdminDTO, criteria);
        getAlias(criteria);
        getSort(userAdminDTO, criteria);
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> searchUser(UserAdminDTO userAdminDTO) {
        Criteria criteria = this.getSessionFactory()
                .getCurrentSession()
                .createCriteria(User.class);
        getAlias(criteria);

        //choose role
        criteria.add(Restrictions.like("roles.type", userAdminDTO.getRole(), MatchMode.ANYWHERE));
        if (userAdminDTO.getStatus().equals("All")) {
            criteria.add(Restrictions.conjunction());
        } else {
            criteria.add(Restrictions.eq("enabled", Boolean.parseBoolean(userAdminDTO.getStatus())));
        }
        if (userAdminDTO.getAllField() != null) {
            criteria.add(searchInAllFields(userAdminDTO.getAllField()));
            userAdminDTO.setTotalPage(getTotalPages(criteria, userAdminDTO.getPageSize()));
            getPagination(userAdminDTO, criteria);
            getSort(userAdminDTO, criteria);
            return criteria.list();
        }
        criteria.add(searchInChosenField(userAdminDTO));
        userAdminDTO.setTotalPage(getTotalPages(criteria, userAdminDTO.getPageSize()));
        getPagination(userAdminDTO, criteria);
        getSort(userAdminDTO, criteria);
        return criteria.list();
    }

    //utilities methods
    //search in all fields
    private Disjunction searchInAllFields(String value) {
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.ilike("email", value, MatchMode.ANYWHERE));
        disjunction.add(Restrictions.ilike("detail.firstName", value, MatchMode.ANYWHERE));
        disjunction.add(Restrictions.ilike("detail.lastName", value, MatchMode.ANYWHERE));
        return disjunction;
    }

    //search in all chosen fields
    private Disjunction searchInChosenField(UserAdminDTO userAdminDTO) {
        return Restrictions.or(Restrictions.like("email", userAdminDTO.getEmail(), MatchMode.ANYWHERE).ignoreCase(),
                Restrictions.like("detail.firstName", userAdminDTO.getFirstName(), MatchMode.ANYWHERE).ignoreCase(),
                Restrictions.like("detail.lastName", userAdminDTO.getLastName(), MatchMode.ANYWHERE).ignoreCase());
    }

    //get count of page
    private Integer getTotalPages(Criteria criteria, Integer pageSize) {
        Long countPages = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        criteria.setProjection(null);
        return (int) Math.ceil((double) countPages / pageSize);
    }

    //get pagination
    private Criteria getPagination(UserAdminDTO userAdminDTO, Criteria criteria) {
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setFirstResult((userAdminDTO.getCurrentPage() - 1) * userAdminDTO.getPageSize());
        criteria.setMaxResults(userAdminDTO.getPageSize());
        return criteria;
    }

    //get alias
    private Criteria getAlias(Criteria criteria) {
        criteria.createAlias("userRoles", "roles");
        criteria.createAlias("userDetails", "detail");
        return criteria;
    }
    //get sort
    /**
     * @param userAdminDTO
     * @param criteria
     * sorting field id in view must be the same to field of User class (or UserDetails with alias)
     * Sort by default - ASC by email
     */
    private Criteria getSort(UserAdminDTO userAdminDTO, Criteria criteria) {
        if (userAdminDTO.getAsc()) {
            criteria.addOrder(Order.asc(userAdminDTO.getSort()));
        } else {
            criteria.addOrder(Order.desc(userAdminDTO.getSort()));
        }
        return criteria;
    }

    //Illia

    @Override
    public List<User> getByRole(String role) {
        Criteria criteria = this.currentSession().createCriteria(User.class, "user")
                .createAlias("user.userRoles", "userRoles").add(Restrictions.eq("userRoles.type", role))
                .createAlias("user.userDetails", "details");
        return criteria.list();
    }

    @Override
    public List<User> getByRole(String role, int pageNumber, int pageSize, String sortBy, Boolean order) {
        Criteria criteria;

        if (sortBy.equals("firstName")) {
            criteria = prepareGetByRole(role, pageNumber, pageSize, "details.firstName", order);
        }
        else if (sortBy.equals("lastName")) {
            criteria = prepareGetByRole(role, pageNumber, pageSize, "details.lastName", order);
        } else {
            criteria = prepareGetByRole(role, pageNumber, pageSize, "user.email", order);
        }

        return criteria.list();
    }

    @Override
    public List<User> searchByRole(String role, String search, int pageNumber, int pageSize, String sortBy, Boolean order) {
        Criteria criteria;
        if (sortBy.equals("firstName")) {
            criteria = prepareSearchByRole(role, search, pageNumber, pageSize, "details.firstName", order);
        }
        else if (sortBy.equals("lastName")) {
            criteria = prepareSearchByRole(role, search, pageNumber, pageSize, "details.lastName", order);
        } else {
            criteria = prepareSearchByRole(role, search, pageNumber, pageSize, "user.email", order);
        }

        return criteria.list();
    }

    @Override
    public Long countOfUsersByRole(String role) {
        Criteria criteria = roleCriteria(role);
        criteria.setProjection(Projections.rowCount());
        Long count = (Long) criteria.uniqueResult();
        return count;
    }

    @Override
    public Long countOfUsersByRole(String role, String search) {
        Criteria criteria = roleCriteria(role, search);
        criteria.setProjection(Projections.rowCount());
        Long count = (Long) criteria.uniqueResult();
        return count;
    }

    private Criterion notNullCriterion() {
        Criterion criterion = Restrictions.and()
                .add(Restrictions.isNotNull("details.patientCard"))
                .add(Restrictions.isNotNull("details.firstName"))
                .add(Restrictions.isNotNull("details.lastName"));
        return criterion;
    }

    private Criteria roleCriteria(String role, String search) {
        Criteria criteria = this.currentSession().createCriteria(User.class, "user").add(Restrictions.isNotNull("user.userDetails"))
                .createAlias("user.userRoles", "userRoles")
                .add(Restrictions.eq("userRoles.type", role))
                .add(notNullCriterion())
                .createAlias("user.userDetails", "details").add(searchCriterion(search));
        return criteria;
    }

    private Criterion searchCriterion(String search) {
        Criterion criterion = Restrictions.or(
                Restrictions.ilike("user.email", search, MatchMode.ANYWHERE),
                Restrictions.ilike("details.firstName", search, MatchMode.ANYWHERE),
                Restrictions.ilike("details.lastName", search, MatchMode.ANYWHERE)
        );
        return criterion;
    }

    private Criteria roleCriteria(String role) {
        Criteria criteria = this.currentSession().createCriteria(User.class, "user").add(Restrictions.isNotNull("user.userDetails"))
                .createAlias("user.userRoles", "userRoles")
                .add(Restrictions.eq("userRoles.type", role))
                .createAlias("user.userDetails", "details")
                .add(notNullCriterion());
        return criteria;
    }

    private Criteria prepareGetByRole(String role, int pageNumber, int pageSize, String sortBy, Boolean order) {
        Criteria criteria;
        if (order) {
            criteria = roleCriteria(role)
                    .addOrder(Order.asc(sortBy));
            criteria.setFirstResult((pageNumber - 1) * pageSize);
            criteria.setMaxResults(pageSize);
        } else {
            criteria = roleCriteria(role)
                    .addOrder(Order.desc(sortBy));
            criteria.setFirstResult((pageNumber - 1) * pageSize);
            criteria.setMaxResults(pageSize);
        }
        return criteria;
    }

    private Criteria prepareSearchByRole(String role, String search, int pageNumber, int pageSize, String sortBy, Boolean order) {
        Criteria criteria;
        if (order) {
            criteria = roleCriteria(role)
                    .add(searchCriterion(search)
                    ).addOrder(Order.asc(sortBy));
            criteria.setFirstResult((pageNumber - 1) * pageSize);
            criteria.setMaxResults(pageSize);
        } else {
            criteria = roleCriteria(role)
                    .add(searchCriterion(search)
                    ).addOrder(Order.desc(sortBy));
            criteria.setFirstResult((pageNumber - 1) * pageSize);
            criteria.setMaxResults(pageSize);
        }
        return criteria;
    }
}



