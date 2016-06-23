package com.hospitalsearch.service.impl;

import com.hospitalsearch.entity.VerificationToken;
import com.hospitalsearch.service.PasswordResetTokenService;
import com.hospitalsearch.service.UserService;
import com.hospitalsearch.service.VerificationTokenService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author Andrew Jasinskiy on 22.06.16
 */
@Service
public class ScheduledTokensService {

    private final Logger logger = LogManager.getLogger(ScheduledTokensService.class);

    @Autowired
    PasswordResetTokenService passwordResetTokenService;

    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    UserService userService;

    @Scheduled(cron = "0 0 12  * * ?")
    public void deleteResetPasswordToken() {
        passwordResetTokenService.deleteExpiredTokens();
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void deleteVerificationToken() {
        try {
            for (VerificationToken token : verificationTokenService.getAllExpiredTokens()) {
                long userId = token.getUser().getId();
                verificationTokenService.delete(token);
                userService.delete(userId);
            }
            logger.info("Delete all users with expired verification tokens.");
        } catch (Exception e) {
            logger.error("Error deleting all users with expired verification tokens:" + e);
        }
    }
}
