package com.hospitalseeker;

import java.util.HashSet;
import java.util.Set;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.hospitalsearch.config.SpringRootConfig;
import com.hospitalsearch.controller.HospitalController;

import nz.net.ultraq.thymeleaf.LayoutDialect;


@Configuration
@Import(value={SpringRootConfig.class})
public class TestConfig {

	@Bean
	public SpringResourceTemplateResolver templateResolver(){
		return new SpringResourceTemplateResolver() {{
			setTemplateMode("HTML5");
			setPrefix("/WEB-INF/pages/");
			setSuffix(".html");
		}};
	}

	//thymeleaf
	@Bean
	public SpringTemplateEngine templateEngine(){
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(templateResolver());
		Set<IDialect> additionalDialects = new HashSet<>();
		additionalDialects.add(new LayoutDialect());
		additionalDialects.add(new SpringSecurityDialect());
		engine.setAdditionalDialects(additionalDialects);
		return engine;
	}

	@Bean
	public ViewResolver viewResolver(){
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setCharacterEncoding("UTF-8");
		return viewResolver;
	}

	@Bean
	public HospitalController hospitalController(){
		return Mockito.mock(HospitalController.class);
	} 
}
