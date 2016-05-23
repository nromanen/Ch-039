package com.hospitalseeker;


import javax.annotation.Resource;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.hospitalsearch.config.SpringRootConfig;
import com.hospitalsearch.config.web.WebConfig;
import com.hospitalsearch.controller.HospitalController;
import com.hospitalsearch.service.UserService;

@ContextConfiguration(classes={SpringRootConfig.class,WebConfig.class})
@WebAppConfiguration
@Test(groups={"content"},suiteName="HospitalSeekerTests",testName="TestCase1")
public class TestDataDB extends AbstractTestNGSpringContextTests{

  @Autowired
  private WebApplicationContext webContext;
  
  @Resource
  Environment properties;

  @Mock
  private UserService service;
  
  private MockMvc standaloneMock;
  private MockMvc webContextMock;
  /**
    <p>do some preparation before test start</p>
  */

  
  @BeforeTest
  public void suiteSetUp(){
	  	  
  }

  /**
    <p>do some preparation before test method run</p>
    this method invoke every time before next test method
  */
  @BeforeMethod
  public void setUp(){
	  
  }

  @Test(enabled=false)
  public void shouldLoadHospitalPage() throws Exception{
	  this.webContextMock = webAppContextSetup(webContext).build();
	  webContextMock.perform(get("/find").accept(MediaType.parseMediaType("text/html")))
	  .andExpect(status().isOk())
	  .andExpect(content().contentType("text/html"));
  }

  @Test(enabled=false)
  public void shouldLoadMainPage() throws Exception{
	  this.standaloneMock = standaloneSetup(HospitalController.class).build();
	  standaloneMock.perform(get("/").accept(MediaType.parseMediaType("text/html")))
	  .andExpect(status().isOk())
	  .andExpect(content().contentType("text/html"));
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
  @AfterTest
  public void suiteTearDown(){
	  
  }
}
