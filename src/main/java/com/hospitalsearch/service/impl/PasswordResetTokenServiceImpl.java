package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.PasswordResetTokenDAO;
import com.hospitalsearch.entity.PasswordResetToken;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.PasswordResetTokenService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author Andrew Jasinskiy on 20.06.16
 */
@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final Logger logger = LogManager.getLogger(PasswordResetTokenServiceImpl.class);

    @Autowired
    private PasswordResetTokenDAO resetTokenDAO;

    @Override
    public PasswordResetToken getByUser(User user) {
        return resetTokenDAO.getByUser(user);
    }

    @Override
    public PasswordResetToken getByToken(String token) {
        return resetTokenDAO.getByToken(token);
    }

    @Override
    public void deleteTokenByUser(User user) {
        try {
            logger.info("delete reset token by user: " + user);
            resetTokenDAO.deleteTokenByUser(user);
        } catch (Exception e) {
            logger.error("Error deleting reset token: " + e);
        }
    }

    @Override
    public void delete(PasswordResetToken token) {
        try {
            logger.info("delete reset token");
            resetTokenDAO.delete(token);
        } catch (Exception e) {
            logger.error("Error deleting reset token: " + e);
        }
    }

    @Override
    public void createToken(String token, User user) {
        try {
            logger.info("create reset token by user: " + user);
            PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
            resetTokenDAO.save(passwordResetToken);
        } catch (Exception e) {
            logger.error("Error creating reset token: " + e);
        }
    }

    @Override
    public void deleteExpiredTokens() {
        try {
            logger.info("Delete all reset password expired tokens.");
            resetTokenDAO.deleteExpiredTokens();
        } catch (Exception e) {
            logger.error("Error deleting all reset password expired tokens " + e);
        }
    }
}
