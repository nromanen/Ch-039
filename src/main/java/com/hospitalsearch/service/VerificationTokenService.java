package com.hospitalsearch.service;

import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.VerificationToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Andrew Jasinskiy on 19.06.16
 */
@Transactional
public interface VerificationTokenService {

    VerificationToken getByUser(User user);

    VerificationToken getByToken(String token);

    void deleteTokenByUser(User user);

    void delete(VerificationToken token);

    void createToken(String token, User user);

    List<VerificationToken> getAllExpiredTokens();
}
