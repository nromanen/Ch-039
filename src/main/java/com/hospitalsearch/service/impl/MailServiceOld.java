/*
package com.hospitalsearch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
public class MailServiceOld {

    @Autowired
    private Environment properties;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private VelocityConfigurer configurer;


    public void createMessage(String emailTo, String subject, String templateName, Map<String, Object> map) {
        String encoding = properties.getProperty("email.encoding");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, encoding);

        try {
            messageHelper.setFrom(mailSender.getUsername());
            messageHelper.setTo(emailTo);
            messageHelper.setSubject(subject);

            String emailBody = VelocityEngineUtils.mergeTemplateIntoString(configurer.getVelocityEngine(), templateName, encoding, map);
            message.setContent(emailBody, "text/html");
            mailSender.send(message);

        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }

    }
}
*/
