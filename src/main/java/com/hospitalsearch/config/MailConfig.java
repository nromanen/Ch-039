package com.hospitalsearch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.Resource;
import java.util.Properties;

@Configuration
@PropertySource(value = "classpath:email.properties")
public class MailConfig {

    @Resource
    Environment properties;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setHost(properties.getProperty("mail.host"));
        mailSender.setPort(Integer.valueOf(properties.getProperty("mail.port")));
        mailSender.setUsername(properties.getProperty("mail.username"));
        mailSender.setPassword(properties.getProperty("mail.password"));

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.transport.protocol", properties.getProperty("mail.transport.protocol"));
        javaMailProperties.put("mail.smtp.auth", properties.getProperty("mail.smtp.auth"));
        javaMailProperties.put("mail.smtp.starttls.enable", properties.getProperty("mail.smtp.starttls.enable"));
        javaMailProperties.put("mail.smtp.quitwait", properties.getProperty("mail.smtp.quitwait"));
        javaMailProperties.put("mail.debug", "true"); //enable to show debug
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }
    //enable gmail "less secure on" https://www.google.com/settings/security/lesssecureapps page
}
