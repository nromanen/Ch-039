package com.hospitalseeker.service;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.hospitalsearch.service.FeedbackService;

@ContextConfiguration
@WebAppConfiguration
@Test(testName="ServiceTest")
//@TestExecutionListeners(listeners={ServletTestExecutionListener.class,
//        DependencyInjectionTestExecutionListener.class,
//        DirtiesContextTestExecutionListener.class,
//        TransactionalTestExecutionListener.class})

public class TestFeedbackService extends AbstractTransactionalTestNGSpringContextTests{
	@Mock private FeedbackService feedbackService;
	
	@BeforeTest
	public void suiteSetUp(){ MockitoAnnotations.initMocks(this); }

	/**
    <p>do some preparation before test method run</p>
    this method invoke every time before next test method
	 */
	@BeforeMethod
	public void setUp(){ 
		
	}
	

	
	/**
	 * do some dispose behaviour
	 */
	@AfterMethod
	public void tearDown(){ }
	/**
	 * do some dispose operation for resources, which were used in suite
	 */
	@AfterTest
	public void suiteTearDown(){

	}

}
