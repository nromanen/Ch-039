package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.VerificationTokenDAO;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.VerificationToken;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Andrew Jasinskiy on 19.06.16
 */
@Repository
public class VerificationTokenDAOImpl extends GenericDAOImpl<VerificationToken, Long> implements VerificationTokenDAO {

    @Autowired
    public VerificationTokenDAOImpl(SessionFactory factory) {
        this.setSessionFactory(factory);
    }

    @Override
    public void deleteTokenByUser(User user) {
        super.delete(getByUser(user));
    }

    @Override
    public VerificationToken getByUser(User user) {
        Query query = this.currentSession().getNamedQuery("GET_VERIFICATION_TOKEN_BY_USER").setParameter("user", user);
        return (VerificationToken) query.uniqueResult();
    }

    @Override
    public VerificationToken getByToken(String token) {
        Query query = this.currentSession().getNamedQuery("GET_VERIFICATION_TOKEN_BY_NAME").setParameter("token", token);
        return (VerificationToken) query.uniqueResult();
    }

    @Override
    public List<VerificationToken> getAllExpiredTokens() {
        Criteria criteria = this.getSessionFactory()
                .getCurrentSession()
                .createCriteria(VerificationToken.class);
        Date today = new Date();
        criteria.add(Restrictions.le("expiryDate", today));
        return criteria.list();
    }
}
