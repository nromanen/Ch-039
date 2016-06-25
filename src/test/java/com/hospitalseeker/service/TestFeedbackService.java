package com.hospitalseeker.service;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.service.FeedbackService;
import com.hospitalseeker.TestConfig;

@ContextConfiguration(classes={TestConfig.class})
@WebAppConfiguration
@Test(testName="ServiceTest")
@TestExecutionListeners(listeners={ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class})

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
	
	public void testSaveNewFeedbackToDatabase(){
		Feedback feedback = new Feedback();
		feedback.setId(1L);
		feedbackService.save(feedback);
		verify(feedbackService,times(1)).save(feedback);
		verify(feedbackService,atLeast(1)).save(feedback);
		when(feedbackService.getById(1L)).thenReturn(feedback);
	}
	
	public void testDeleteFeedbackFromDatabase(){
		Feedback feedback = new Feedback();
		feedback.setId(1L);
		feedbackService.save(feedback);
		feedbackService.delete(feedback);
		verify(feedbackService,times(1)).delete(feedback);
		when(feedbackService.getById(1L)).thenReturn(null);
	}
	
	public void testGetAllHospitalFromDatabase(){
		Feedback feedback = new Feedback();
		feedback.setId(1L);
		
		Feedback feedback2 = new Feedback();
		feedback.setId(2L);
		
		feedbackService.save(feedback);
		feedbackService.save(feedback2);
		
		List<Feedback> feedbacks = new ArrayList<>();
		feedbacks.add(feedback);
		feedbacks.add(feedback2);
		verify(feedbackService,atLeast(1)).save(feedback);
		when(feedbackService.getAll()).thenReturn(feedbacks);
	}
	
	public void testUpdateHospitalInDatabase(){
		Feedback feedback = new Feedback();
		feedback.setId(1L);
		feedbackService.save(feedback);
		doReturn(feedback).when(feedbackService).getById(1L);
		
		feedback = feedbackService.getById(1L);
		feedback.setMessage("new");
		feedbackService.update(feedback);
		
		verify(feedbackService,atLeast(1)).update(feedback);
		doReturn(feedback).when(feedbackService).getById(1L);
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
