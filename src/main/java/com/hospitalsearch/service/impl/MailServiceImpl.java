package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.AppointmentDAO;
import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.MailService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Andrew Jasinskiy on 19.06.16
 */
@Service
public class MailServiceImpl implements MailService {

    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private ServletContext context;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private VelocityConfigurer configurer;

    @Autowired
    private Environment properties;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AppointmentDAO appointmentDAO;

    @Override
    public void sendMessage(User user, String subject, String text, String templateName) throws ConnectException {
        if (!pingURL("https://www.google.com.ua/", 300)) {
            throw new MailSendException("Connection failed");
        }
        String encoding = properties.getProperty("email.encoding");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, encoding);
        Map<String, Object> hTemplateVariables = new HashMap<>();
        hTemplateVariables.put("message", text);
        try {
            messageHelper.setFrom(mailSender.getUsername());
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject(subject);
            String emailBody = VelocityEngineUtils.mergeTemplateIntoString(configurer.getVelocityEngine(), templateName, encoding, hTemplateVariables);
            message.setContent(emailBody, "text/html; charset=UTF-8");
            synchronized (message) {
                mailSender.send(message);
            }
        } catch (MessagingException e) {
            logger.error("Cant't send message");
            logger.error(e);
        }
    }

    @Override
    public String createBannedMessage(User user, Locale locale) {
        return messageSource.getMessage("mail.message.banned.prefix", null, locale) + " " + user.getEmail() + " " +
                messageSource.getMessage("mail.message.banned.suffix", null, locale);
    }

    private String buildConfirmationURL(String token, String path) {
        return "https://" + request.getServerName() + ":" + request.getServerPort() + context.getContextPath()
                + path + token;
    }

    @Override
    public String createRegisterMessage(User user, String token, Locale locale) {
        return messageSource.getMessage("mail.message.registration.prefix", null, locale) + " " +
                user.getEmail() + " " + messageSource.getMessage("mail.message.registration.text", null, locale) +
                buildConfirmationURL(token, "/confirmRegistration?token=") +
                messageSource.getMessage("mail.message.registration.suffix", null, locale);
    }

    @Override
    public String createResetPasswordMessage(User user, String token, Locale locale) {
        return messageSource.getMessage("mail.message.registration.prefix", null, locale) + " " + user.getEmail() +
                messageSource.getMessage("mail.message.forgot.password.text", null, locale) + buildConfirmationURL(token, "/confirmResetPassword?token=") +
                messageSource.getMessage("mail.message.forgot.password.suffix", null, locale);
    }

    @Override
    public void sendMassageFromUserToUser(Map<String, String> massageData, Locale locale) throws ConnectException {
        String doctorEmail = userDAO.getById(appointmentDAO.getById(Long.parseLong(massageData.get("eventId"))).getDoctorInfo().getUserDetails().getId()).getEmail();
        String patientEmail = userDAO.getById(appointmentDAO.getById(Long.parseLong(massageData.get("eventId"))).getUserDetail().getId()).getEmail();
        String principalEmail = massageData.get("principal");
        User user = null;
        if (doctorEmail.equals(principalEmail)){
            user = userDAO.getByEmail(patientEmail);
            sendMessage(user, messageSource.getMessage("mail.massage.cancel.appointment", null, locale), massageData.get("principalMassage"), "emailTemplate.vm");
        }else {
            user = userDAO.getByEmail(doctorEmail);
            sendMessage(user, messageSource.getMessage("mail.massage.cancel.appointment", null, locale), massageData.get("principalMassage"), "emailTemplate.vm");
        }
    }

    //ping some url
    public static boolean pingURL(String url, int timeout) {
        url = url.replaceFirst("^https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (IOException e) {
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
