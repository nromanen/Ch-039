package com.hospitalsearch.config.web;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import nz.net.ultraq.thymeleaf.LayoutDialect;



@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.hospitalsearch"})
public class WebConfig extends WebMvcConfigurerAdapter{

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        return new SpringResourceTemplateResolver() {{
        	setTemplateMode("HTML5");
        	setPrefix("/WEB-INF/pages/");
        	setSuffix(".html");
        }};
    }
    
    @Bean
    public SpringTemplateEngine templateEngine(){
    	SpringTemplateEngine engine = new SpringTemplateEngine();
    	engine.setTemplateResolver(templateResolver());
    	Set<IDialect> additionalDialects = new HashSet<>();
    	additionalDialects.add(new LayoutDialect());
		engine.setAdditionalDialects(additionalDialects);
    	return engine;
    }
    
    @Bean
    public ViewResolver themeleafViewResolver(){
    	return new ThymeleafViewResolver(){{
    		setTemplateEngine(templateEngine());
    		setCharacterEncoding("UTF-8");
    	}};
    }
    
    @Bean
    public LocaleResolver LocaleResolver(){
    	CookieLocaleResolver resolver = new CookieLocaleResolver();
    	resolver.setDefaultLocale(new Locale("en"));
    	resolver.setCookieMaxAge(100000);
    	return resolver;
    }
    
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		registry.addInterceptor(interceptor);
	}
    
    
    
}
