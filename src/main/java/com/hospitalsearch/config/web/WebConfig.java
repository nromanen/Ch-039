package com.hospitalsearch.config.web;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.hospitalsearch.config.SpringRootConfig;
import com.hospitalsearch.util.RoleConverter;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@EnableWebMvc
@EnableCaching
@ComponentScan(basePackages = {"com.hospitalsearch"},basePackageClasses={SpringRootConfig.class})
public class WebConfig extends WebMvcConfigurerAdapter{

	@Autowired
	RoleConverter roleConverter;

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




	//crypto
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(roleConverter);
	}

	//thymeleaf
	@Bean
	public SpringTemplateEngine templateEngine(){
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(templateResolver());
		engine.setMessageSource(messageSource());
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

    @Bean(name="localeResolver")
    public LocaleResolver localeResolver(){
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(new Locale("en"));
        resolver.setCookieMaxAge(100000);
        return resolver;
    }

    @Bean
    public MessageSource messageSource(){
        return new ReloadableResourceBundleMessageSource(){{
            setBasename("classpath:i18n/messages");
        }};
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        registry.addInterceptor(interceptor);
    }

	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
		return new EhCacheManagerFactoryBean(){
			{
				setConfigLocation(new ClassPathResource("ehcache.xml"));
				setShared(true);
			}
		};

	}

	@Bean
	public CacheManager cacheManager(){
		return new EhCacheCacheManager(ehCacheManagerFactoryBean().getObject());
	}


}



