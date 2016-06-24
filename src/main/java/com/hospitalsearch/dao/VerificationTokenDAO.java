package com.hospitalsearch.dao;

import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.VerificationToken;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Andrew Jasinskiy on 19.06.16
 */
@Component
public interface VerificationTokenDAO extends GenericDAO<VerificationToken,Long> {

    VerificationToken getByUser(User user);

    VerificationToken getByToken(String token);

    void deleteTokenByUser(User user);

    List<VerificationToken> getAllExpiredTokens();
}
