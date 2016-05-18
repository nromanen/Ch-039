package com.hospitalseeker;


import org.testng.annotations.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.hospitalsearch.service.UserService;
import org.testng.Assert;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
@WebAppConfiguration
@ContextConfiguration(classes={com.hospitalsearch.config.SpringRootConfig.class})
public class TestDataDB extends AbstractTestNGSpringContextTests{

  private static final String PROP_DATABASE_DRIVER = "db.driver";
  private static final String PROP_DATABASE_PASSWORD = "db.password";
  private static final String PROP_DATABASE_URL = "db.url";
  private static final String PROP_DATABASE_USERNAME = "db.username";
  private static final String PROP_HIBERNATE_DIALECT = "hibernate.dialect";
  private static final String PROP_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
  private static final String PROP_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
  private static final String PROP_HIBERNATE_ENTITY_PACKAGE = "hibernate.entity.package";
  private static final String PROP_HIBERNATE_IMPORT_FILE = "hibernate.hbm2ddl.import_files";
  private static final String PROP_FILE_PATH="classpath:app.properties";
  private static final int PROP_COUNT = 9;
  @Autowired
  private UserService userService;

  @Resource
	Environment properties;

  /**
    <p>do some preparation before suite start</p>
  */

  @BeforeSuite
  public void suiteSetUp(){

  }

  /**
    <p>do some preparation before test method run</p>
    this method invoke every time before next test method
  */
  @BeforeMethod
  public void setUp(){

  }

  @Test(groups={"db","configs"},suiteName="HospitalSeekerTests")
  public void shouldConfigureDataSource() throws IOException{
    Assert.assertNotNull(properties);
    Assert.assertEquals(properties.getProperty(PROP_DATABASE_DRIVER, "not"), "org.postgresql.Driver");
  }


  @Test(groups="db",dependsOnGroups={"configs"},suiteName="HospitalSeekerTests")
  public void shouldLoadDataFromUsersDB(){
    Assert.assertNotNull(userService);
    Assert.assertNotNull(userService.getAll());
    Assert.assertNotNull(userService.getById(2L));
    Assert.assertEquals(userService.getByRole(1000L).size(),0,"size of users list must be 0");
  }


  /**
   * do some dispose behaviour
   */
  @AfterMethod
  public void tearDown(){

  }
  /**
   * do some dispose operation for resources, which were used in suite
   */
  @AfterSuite
  public void suiteTearDown(){

  }
}
