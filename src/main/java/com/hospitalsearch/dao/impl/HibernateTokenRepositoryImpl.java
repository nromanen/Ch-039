package com.hospitalsearch.dao.impl;

import com.hospitalsearch.entity.PersistentLogin;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by andrew on 16.05.16.
 */

@Repository("tokenRepositoryDao")
@Transactional
public class HibernateTokenRepositoryImpl extends GenericDAOImpl<PersistentLogin,String> implements PersistentTokenRepository {

    @Autowired
    public HibernateTokenRepositoryImpl(SessionFactory factory) {
        super();
        this.setSessionFactory(factory);
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        PersistentLogin persistentLogin = new PersistentLogin();
        persistentLogin.setUsername(token.getUsername());
        persistentLogin.setSeries(token.getSeries());
        persistentLogin.setToken(token.getTokenValue());
        persistentLogin.setLast_used(token.getDate());
        save(persistentLogin);
    }

    @Override
    public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
        PersistentLogin persistentLogin = getById(seriesId);
        persistentLogin.setToken(tokenValue);
        persistentLogin.setLast_used(lastUsed);
        update(persistentLogin);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        try {
            Criteria criteria = this.currentSession().createCriteria(PersistentLogin.class);
            criteria.add(Restrictions.eq("series", seriesId));
            PersistentLogin persistentLogin = (PersistentLogin) criteria.uniqueResult();

            return new PersistentRememberMeToken(persistentLogin.getUsername(), persistentLogin.getSeries(),
                    persistentLogin.getToken(), persistentLogin.getLast_used());
        } catch (Exception e) {
            System.out.println("Token not found...");
            return null;
        }
    }

    @Override
    public void removeUserTokens(String username){
        Criteria criteria = this.currentSession().createCriteria(PersistentLogin.class);
        PersistentLogin persistentLogin = (PersistentLogin) criteria.uniqueResult();
        if (persistentLogin != null) {
            System.out.println("rememberMe was selected");
            delete(persistentLogin);
        }
    }
}
