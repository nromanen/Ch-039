package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.PasswordResetTokenDAO;
import com.hospitalsearch.entity.PasswordResetToken;
import com.hospitalsearch.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Andrew Jasinskiy on 20.06.16
 */
@Repository
public class PasswordResetTokenDAOImpl extends GenericDAOImpl<PasswordResetToken, Long> implements PasswordResetTokenDAO {

    @Autowired
    public PasswordResetTokenDAOImpl(SessionFactory factory) {
        this.setSessionFactory(factory);
    }

    @Override
    public void deleteTokenByUser(User user) {
        super.delete(getByUser(user));
    }

    @Override
    public PasswordResetToken getByUser(User user) {
        Query query = this.currentSession().getNamedQuery("GET_RESET_PASSWORD_TOKEN_BY_USER").setParameter("user", user);
        return (PasswordResetToken) query.uniqueResult();
    }

    @Override
    public PasswordResetToken getByToken(String token) {
        Query query = this.currentSession().getNamedQuery("GET_RESET_PASSWORD_TOKEN_BY_NAME").setParameter("token", token);
        return (PasswordResetToken) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void deleteExpiredTokens() {
        Criteria criteria = this.getSessionFactory()
                .getCurrentSession()
                .createCriteria(PasswordResetToken.class);
        Date today = new Date();
        criteria.add(Restrictions.le("expiryDate", today));
        List<PasswordResetToken> tokens = criteria.list();
        tokens.forEach(this::delete);
    }
}
