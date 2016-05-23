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
  
  }


  @Test(groups="db",dependsOnGroups={"configs"},suiteName="HospitalSeekerTests")
  public void shouldLoadDataFromUsersDB(){
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
