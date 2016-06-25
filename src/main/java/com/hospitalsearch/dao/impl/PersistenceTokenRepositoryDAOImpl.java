package com.hospitalsearch.dao.impl;

import com.hospitalsearch.entity.PersistentLogin;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
 * @author Andrew Jasinskiy on 20.06.16
 */
@Repository("tokenRepositoryDao")
@Transactional
public class PersistenceTokenRepositoryDAOImpl extends GenericDAOImpl<PersistentLogin,String> implements PersistentTokenRepository {

    private final Logger logger = LogManager.getLogger(PersistenceTokenRepositoryDAOImpl.class);

    @Autowired
    public PersistenceTokenRepositoryDAOImpl(SessionFactory factory) {
        super();
        this.setSessionFactory(factory);
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        logger.info("Creating Token for user : {}"+ token.getUsername());
        PersistentLogin persistentLogin = new PersistentLogin();
        persistentLogin.setUsername(token.getUsername());
        persistentLogin.setSeries(token.getSeries());
        persistentLogin.setToken(token.getTokenValue());
        persistentLogin.setLast_used(token.getDate());
        save(persistentLogin);
    }

    @Override
    public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
        logger.info("Updating Token for seriesId : {}" + seriesId);
        PersistentLogin persistentLogin = getById(seriesId);
        persistentLogin.setToken(tokenValue);
        persistentLogin.setLast_used(lastUsed);
        update(persistentLogin);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String series) {
        logger.info("Fetch Token if any for seriesId : {}" + series);
        try {
            Criteria criteria = this.currentSession().createCriteria(PersistentLogin.class);
            criteria.add(Restrictions.eq("series", series));
            PersistentLogin persistentLogin = (PersistentLogin) criteria.uniqueResult();
            return new PersistentRememberMeToken(persistentLogin.getUsername(), persistentLogin.getSeries(),
                    persistentLogin.getToken(), persistentLogin.getLast_used());
        } catch (Exception e) {
            logger.error("Token not found..." + e);
            return null;
        }
    }

    @Override
    public void removeUserTokens(String username){
        logger.info("Removing Token if any for user : {}" + username);
        Criteria criteria = this.currentSession().createCriteria(PersistentLogin.class);
        criteria.add(Restrictions.eq("username", username));
        PersistentLogin persistentLogin = (PersistentLogin) criteria.uniqueResult();
        if (persistentLogin != null) {
            logger.info("rememberMe was selected");
            delete(persistentLogin);
        }
    }
}
