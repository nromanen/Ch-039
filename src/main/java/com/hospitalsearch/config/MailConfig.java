package com.hospitalsearch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import javax.annotation.Resource;
import java.util.Properties;

@Configuration
@PropertySource(value = "classpath:email.properties")
public class MailConfig {

    @Resource
    Environment properties;

    @Bean
    public JavaMailSenderImpl javaMailSenderImpl() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(properties.getProperty("email.host"));
        mailSender.setPort(Integer.parseInt(properties.getProperty("email.port")));
        mailSender.setUsername(properties.getProperty("email.email"));
        mailSender.setPassword(properties.getProperty("email.password"));

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true"); //enable to show debug
        return mailSender;
    }
    //enable gmail "less secure on" https://www.google.com/settings/security/lesssecureapps page
    

    @Bean
    public VelocityConfigurer velocityConfigurer() {
        VelocityConfigurer configurer = new VelocityConfigurer();
        configurer.setResourceLoaderPath("/WEB-INF/velocity/");
        return configurer;
    }

}
