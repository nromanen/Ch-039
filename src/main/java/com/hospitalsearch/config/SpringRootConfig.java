package com.hospitalsearch.config;

import com.hospitalsearch.validator.ImageValidator;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by speedfire on 4/28/16.
 */

//here is all configuration for spring parts
@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:app.properties")
@ComponentScan(basePackages = "com.hospitalsearch")
@EnableCaching
@Import({MailConfig.class})
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
    private static final String PROP_HIBERNATE_ENTITY_PACKAGE = "hibernate.entity.package";
    private static final String PROP_HIBERNATE_IMPORT_FILE = "hibernate.hbm2ddl.import_files";
    //private static final String PROP_HIBERNATE_EHCACHE = "hibernate.cache.provider_class";
    private static final String PROP_HIBERNATE_EHCACHE_LEVEL = "hibernate.cache.use_second_level_cache";
    private static final String PROP_HIBERNATE_EHCACHE_REGION_FACTORY = "hibernate.cache.region.factory_class";
    private static final String PROP_HIBERNATE_EHCACHE_SHOWSQL = "hibernate.cache.use_query_cache";
    private static final String PROP_HIBERNATE_SEARCH_DEFAULT_DIRECTORY_PROVIDER = "hibernate.search.default.directory_provider";
    private static final String PROP_HIBERNATE_SEARCH_INDEX_BASE = "hibernate.search.default.indexBase";


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(properties.getRequiredProperty(PROP_DATABASE_DRIVER));
        dataSource.setUrl(properties.getRequiredProperty(PROP_DATABASE_URL));
        dataSource.setUsername(properties.getRequiredProperty(PROP_DATABASE_USERNAME));
        dataSource.setPassword(properties.getRequiredProperty(PROP_DATABASE_PASSWORD));
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        sessionFactoryBean.setPackagesToScan(new String[]{properties.getRequiredProperty(PROP_HIBERNATE_ENTITY_PACKAGE)});
        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactoryBean().getObject());
        return transactionManager;
    }

    public Properties hibernateProperties() {
        Properties props = new Properties();
        props.put(PROP_HIBERNATE_DIALECT, properties.getRequiredProperty(PROP_HIBERNATE_DIALECT));
        props.put(PROP_HIBERNATE_SHOW_SQL, properties.getRequiredProperty(PROP_HIBERNATE_SHOW_SQL));
        //props.put(PROP_HIBERNATE_EHCACHE, properties.getRequiredProperty(PROP_HIBERNATE_EHCACHE));
        props.put(PROP_HIBERNATE_EHCACHE_LEVEL, properties.getRequiredProperty(PROP_HIBERNATE_EHCACHE_LEVEL));
        props.put(PROP_HIBERNATE_EHCACHE_SHOWSQL, properties.getRequiredProperty(PROP_HIBERNATE_EHCACHE_SHOWSQL));
        props.put(PROP_HIBERNATE_EHCACHE_REGION_FACTORY, properties.getRequiredProperty(PROP_HIBERNATE_EHCACHE_REGION_FACTORY));
        props.put(PROP_HIBERNATE_SEARCH_DEFAULT_DIRECTORY_PROVIDER, properties.getRequiredProperty(PROP_HIBERNATE_SEARCH_DEFAULT_DIRECTORY_PROVIDER));
        props.put(PROP_HIBERNATE_SEARCH_INDEX_BASE, properties.getRequiredProperty(PROP_HIBERNATE_SEARCH_INDEX_BASE));
        return props;
    }

    /**
     * @author Oleksandr Mukonin
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase-changeLog.xml");
        liquibase.setDataSource(dataSource());
        liquibase.setIgnoreClasspathPrefix(true);
        return liquibase;
    }

    @Bean
    public ImageValidator imageValidator() {
        return new ImageValidator();
    }
}
