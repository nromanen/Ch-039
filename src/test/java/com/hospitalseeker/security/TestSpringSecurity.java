package com.hospitalseeker.security;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import javax.servlet.Filter;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.hospitalseeker.TestConfig;

@ContextConfiguration(classes={TestConfig.class})
@WebAppConfiguration
@Test(testName="SecurityTest")
@TestExecutionListeners(listeners={
		ServletTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		WithSecurityContextTestExecutionListener.class})
public class TestSpringSecurity extends AbstractTestNGSpringContextTests{

	@Autowired private WebApplicationContext ctx;
	@Autowired private Filter springSecurityFilterChain;

	private MockMvc webContextMock;

	@Mock
	private ViewResolver viewResolver;

	@BeforeTest
	public void suiteSetUp(){

	}

	/**
    <p>do some preparation before test method run</p>
    this method invoke every time before next test method
	 */
	@BeforeMethod
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		this.webContextMock = webAppContextSetup(this.ctx)
				.defaultRequest(get("/")
						.with(testSecurityContext()))
				.addFilter(springSecurityFilterChain).build();
	}


	@Test
	@WithMockUser(roles="ADMIN")
	public void requestProtectedUrlWithAdmin() throws Exception {
		webContextMock
		.perform(get("/admin"))
		.andExpect(authenticated().withUsername("user").withRoles("ADMIN"));
	}

	@Test
	@WithMockUser(roles="USER")
	public void requestProtectedUrlWithUser() throws Exception {
		webContextMock
		.perform(get("/admin"))
		.andExpect(status().isForbidden())
		.andExpect(authenticated().withUsername("user"));
	}



	@Test
	@WithMockUser
	public void requestProtectedFeedbackWithUserAndCsrfToken() throws Exception {
		webContextMock
		.perform(post("/doctor/feedback").with(csrf()))
		.andExpect(status().isBadRequest())
		.andExpect(authenticated().withUsername("user"));
	}
	@WithMockUser(roles="PATIENT")
  @Test(enabled=false)
	public void requestHospitalListPage() throws Exception {
		webContextMock
		.perform(get("/hospitals"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("text/html;charset=ISO-8859-1"));
	}


	public void requestDepartmentListPageByHospitalId() throws Exception {
		webContextMock
		.perform(get("/departments"))
		.andExpect(status().is4xxClientError());
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

	@Configuration
	@EnableWebSecurity
	@EnableWebMvc
	static class Config extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
			.authorizeRequests()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and()
			.formLogin();
		}

		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth
			.inMemoryAuthentication()
			.withUser("user").password("password").roles("USER").and()
			.withUser("patient").password("password").roles("PATIENT").and()
			.withUser("manager").password("password").roles("MANAGER");

		}
	}

}
