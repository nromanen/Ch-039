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

import com.hospitalsearch.entity.Department;
import com.hospitalsearch.service.DepartmentService;
import com.hospitalseeker.TestConfig;
@ContextConfiguration(classes={TestConfig.class})
@WebAppConfiguration
@Test(testName="ServiceTest")
@TestExecutionListeners(listeners={ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class TestDepartmentService extends AbstractTransactionalTestNGSpringContextTests{
	@Mock private DepartmentService departmentService;
	
	@BeforeTest
	public void suiteSetUp(){ MockitoAnnotations.initMocks(this); }

	/**
    <p>do some preparation before test method run</p>
    this method invoke every time before next test method
	 */
	@BeforeMethod
	public void setUp(){ 
		
	}
	
	public void testSaveNewDepartmentToDatabase(){
		Department dep = new Department();
		dep.setId(1L);
		departmentService.save(dep);
		verify(departmentService,times(1)).save(dep);
		verify(departmentService,atLeast(1)).save(dep);
		when(departmentService.getById(1L)).thenReturn(dep);
	}
	
	public void testDeleteDepartmentFromDatabase(){
		Department department = new Department();
		department.setId(1L);
		departmentService.save(department);
		departmentService.delete(department);
		verify(departmentService,times(1)).delete(department);
		when(departmentService.getById(1L)).thenReturn(null);
	}
	
	public void testGetAllDepartmentsFromDatabase(){
		Department department = new Department();
		department.setId(1L);
		
		Department department2 = new Department();
		department.setId(2L);
		
		departmentService.save(department);
		departmentService.save(department2);
		
		List<Department> deps = new ArrayList<>();
		deps.add(department);
		deps.add(department2);
		verify(departmentService,atLeast(1)).save(department);
		when(departmentService.getAll()).thenReturn(deps);
	}
	
	public void testUpdateDepartmentInDatabase(){
		Department department = new Department();
		department.setId(1L);
		departmentService.save(department);
		doReturn(department).when(departmentService).getById(1L);
		
		department = departmentService.getById(1L);
		department.setName("new");
		departmentService.update(department);
		
		verify(departmentService,atLeast(1)).update(department);
		doReturn(department).when(departmentService).getById(1L);
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
