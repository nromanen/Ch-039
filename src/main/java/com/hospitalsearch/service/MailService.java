package com.hospitalsearch.service;

import com.hospitalsearch.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.ConnectException;
import java.util.Locale;
import java.util.Map;

/**
 * @author Andrew Jasinskiy on 19.06.16
 */
@Component
@Transactional
public interface MailService {

    void sendMessage(User user, String subject, String text, String templateName) throws ConnectException;

    String createRegisterMessage(User user, String token, Locale locale);

    String createResetPasswordMessage(User user, String token, Locale locale);

    String createBannedMessage(User user, Locale locale);

    void sendMassageFromUserToUser(Map<String,String> massageData, Locale locale) throws ConnectException;
}
