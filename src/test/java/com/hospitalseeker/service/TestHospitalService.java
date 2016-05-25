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
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.service.HospitalService;
import com.hospitalseeker.TestConfig;
@ContextConfiguration(classes={TestConfig.class})
@WebAppConfiguration
@Test(testName="ServiceTest")
@TestExecutionListeners(listeners={ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class TestHospitalService extends AbstractTestNGSpringContextTests{


	
	@Mock private HospitalService hospitalService;
	
	 
	
	
	

	@BeforeTest
	public void suiteSetUp(){ MockitoAnnotations.initMocks(this); }

	/**
    <p>do some preparation before test method run</p>
    this method invoke every time before next test method
	 */
	@BeforeMethod
	public void setUp(){ 
		
	}
	
	public void testSaveNewHospitalToDatabase(){
		Hospital hospital = new Hospital();
		hospital.setId(1L);
		hospitalService.save(hospital);
		verify(hospitalService,times(1)).save(hospital);
		verify(hospitalService,atLeast(1)).save(hospital);
		when(hospitalService.getById(1L)).thenReturn(hospital);
	}
	
	public void testDeleteHospitalFromDatabase(){
		Hospital hospital = new Hospital();
		hospital.setId(1L);
		hospitalService.save(hospital);
		hospitalService.delete(hospital);
		verify(hospitalService,times(1)).delete(hospital);
		when(hospitalService.getById(1L)).thenReturn(null);
	}
	
	public void testGetAllHospitalsFromDatabase(){
		Hospital hospital = new Hospital();
		hospital.setId(1L);
		Hospital hospital2 = new Hospital();
		hospital.setId(2L);
		hospitalService.save(hospital);
		hospitalService.save(hospital2);
		
		verify(hospitalService,atLeast(1)).save(hospital);
		List<Hospital> hospitals = new ArrayList<>();
		hospitals.add(hospital);
		hospitals.add(hospital2);
		when(hospitalService.getAll()).thenReturn(hospitals);
	}
	
	public void testUpdateHospitalInDatabase(){
		Hospital hospital = new Hospital();
		hospital.setId(1L);
		hospitalService.save(hospital);
		
		doReturn(hospital).when(hospitalService).getById(1L);
		hospital = hospitalService.getById(1L);
		hospital.setName("new");
		hospitalService.update(hospital);
		verify(hospitalService,atLeast(1)).update(hospital);
		doReturn(hospital).when(hospitalService).getById(1L);
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
