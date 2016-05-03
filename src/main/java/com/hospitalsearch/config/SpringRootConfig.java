package com.hospitalsearch.config;


import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by speedfire on 4/28/16.
 */

//here is all configuration for spring parts
@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:app.properties")
@ComponentScan(basePackages="com.hospitalsearch")
public class SpringRootConfig {
	@Resource
	Environment properties;

	private static final String PROP_DATABASE_DRIVER = "db.driver";
	private static final String PROP_DATABASE_PASSWORD = "db.password";
	private static final String PROP_DATABASE_URL = "db.url";
	private static final String PROP_DATABASE_USERNAME = "db.username";
	private static final String PROP_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROP_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROP_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(properties.getRequiredProperty(PROP_DATABASE_DRIVER));
		dataSource.setUrl(properties.getRequiredProperty(PROP_DATABASE_URL));
		dataSource.setUsername(properties.getRequiredProperty(PROP_DATABASE_USERNAME));
		dataSource.setPassword(properties.getRequiredProperty(PROP_DATABASE_PASSWORD));
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactoryBean(){
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setHibernateProperties(hibernateProperties());
		sessionFactoryBean.setPackagesToScan(new String[]{"com.hospitalsearch.entities"});
		return sessionFactoryBean;
	}
	@Bean
	public HibernateTransactionManager hibernateTransactionManager(){
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactoryBean().getObject());
		return transactionManager;
	}

	public Properties hibernateProperties(){
		Properties props = new Properties();
		props.put(PROP_HIBERNATE_DIALECT,properties.getRequiredProperty(PROP_HIBERNATE_DIALECT));
		props.put(PROP_HIBERNATE_HBM2DDL_AUTO,properties.getRequiredProperty(PROP_HIBERNATE_HBM2DDL_AUTO));
		props.put(PROP_HIBERNATE_SHOW_SQL,properties.getRequiredProperty(PROP_HIBERNATE_SHOW_SQL));
		return props;
	}

}
