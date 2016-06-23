package com.hospitalsearch.dao;

import com.hospitalsearch.entity.PasswordResetToken;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.VerificationToken;
import org.springframework.stereotype.Component;

/**
 * @author Andrew Jasinskiy on 20.06.16
 */
@Component
public interface PasswordResetTokenDAO extends GenericDAO<PasswordResetToken,Long>  {

    PasswordResetToken getByUser(User user);

    PasswordResetToken getByToken(String token);

    void deleteTokenByUser(User user);

    void deleteExpiredTokens();
}
