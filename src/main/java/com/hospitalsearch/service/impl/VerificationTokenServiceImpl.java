package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.VerificationTokenDAO;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.VerificationToken;
import com.hospitalsearch.service.VerificationTokenService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Andrew Jasinskiy on 19.06.16
 */
@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final Logger logger = LogManager.getLogger(VerificationTokenServiceImpl.class);

    @Autowired
    private VerificationTokenDAO tokenDAO;

    @Override
    public VerificationToken getByUser(User user) {
        return tokenDAO.getByUser(user);
    }

    @Override
    public VerificationToken getByToken(String token) {
        return tokenDAO.getByToken(token);
    }

    @Override
    public void deleteTokenByUser(User user) {
        try {
            logger.info("delete verification token by user: " + user);
            tokenDAO.deleteTokenByUser(user);
        } catch (Exception e) {
            logger.error("Error deleting verification token: " + e);
        }
    }

    @Override
    public void delete(VerificationToken token) {
        try {
            logger.info("delete verification token");
            tokenDAO.delete(token);
        } catch (Exception e) {
            logger.error("Error deleting verification token: " + e);
        }
    }

    @Override
    public void createToken(String token, User user) {
        try {
            logger.info("create verification token by user: " + user);
            VerificationToken verificationToken = new VerificationToken(token, user);
            tokenDAO.save(verificationToken);
        } catch (Exception e) {
            logger.error("Error creating verification token: " + e);
        }
    }

    @Override
    public List<VerificationToken> getAllExpiredTokens() {
        List<VerificationToken> tokens = null;
        try {
            logger.info("Get all expired verification tokens.");
            tokens= tokenDAO.getAllExpiredTokens();
        } catch (Exception e) {
            logger.error("Error getting all verification tokens." + e);
        }
        return tokens;
    }
}
