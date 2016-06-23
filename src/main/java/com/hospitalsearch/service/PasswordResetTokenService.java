package com.hospitalsearch.service;

import com.hospitalsearch.entity.PasswordResetToken;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.VerificationToken;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Andrew Jasinskiy on 20.06.16
 */
@Transactional
public interface PasswordResetTokenService {

    PasswordResetToken getByUser(User user);

    PasswordResetToken getByToken(String token);

    void deleteTokenByUser(User user);

    void delete(PasswordResetToken token);

    void createToken(String token, User user);

    void deleteExpiredTokens();


}
