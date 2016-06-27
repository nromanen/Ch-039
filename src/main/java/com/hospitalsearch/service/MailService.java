package com.hospitalsearch.service;

import com.hospitalsearch.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.ConnectException;
import java.util.Map;

/**
 * @author Andrew Jasinskiy on 19.06.16
 */
@Component
@Transactional
public interface MailService {

    void sendMessage(User user, String subject, String text, String templateName) throws ConnectException;

    String createRegisterMessage(User user, String token);

    String createResetPasswordMessage(User user, String token);

    String createBannedMessage(User user);
}
