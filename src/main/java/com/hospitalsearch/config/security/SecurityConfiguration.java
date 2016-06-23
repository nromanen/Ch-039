package com.hospitalsearch.config.security;

import com.hospitalsearch.handlers.CustomAuthenticationHandler;
import com.hospitalsearch.handlers.ErrorAuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import javax.sql.DataSource;

/**
 * @author Andrew Jasinskiy
 */
@Configuration
@ComponentScan(basePackages = "com.hospitalsearch")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final Integer TIME = 21600;

	@Autowired
	@Qualifier("CustomUserDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private PersistentTokenRepository tokenRepository;

	@Autowired
	private CustomAuthenticationHandler customHandler;

	@Autowired
	private ErrorAuthenticationHandler errorAuthenticationHandler;

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/hospitals/config");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		http.addFilterBefore(filter, CsrfFilter.class);
		http
				.authorizeRequests()
				.antMatchers("/", "/home").permitAll()
				.antMatchers("/admin/**").access("hasRole('ADMIN')")
				.antMatchers("/manageDoctors").access("hasRole('MANAGER')")
				.antMatchers("/**/manage").access("hasRole('MANAGER')")
				.antMatchers("/editHospitalsManagers").access("hasRole('ADMIN')")
				.antMatchers("/appointments").access("hasRole('PATIENT')")
				.antMatchers("/workscheduler").access("hasRole('DOCTOR')")
				.antMatchers("/login").anonymous()
				.and()
				.formLogin()
				.loginPage("/login")
				.usernameParameter("email")
				.passwordParameter("password")
				.successHandler(customHandler)
				.failureHandler(errorAuthenticationHandler)
				.and().csrf()
				.and()
				.logout()
				.logoutSuccessUrl("/login?logout")
				.invalidateHttpSession(true)
				.deleteCookies("remember-me")
				.and()
				.exceptionHandling()
				.accessDeniedPage("/403")
				.and()
				.rememberMe()
				.rememberMeParameter("remember-me")
				.tokenRepository(tokenRepository)
				.tokenValiditySeconds(TIME);


	}

	//password encoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices() {
		PersistentTokenBasedRememberMeServices tokenBasedservice = new PersistentTokenBasedRememberMeServices(
				"remember-me", userDetailsService, tokenRepository);
		return tokenBasedservice;
	}

	@Bean
	public ErrorAuthenticationHandler errorAuthenticationHandler() {
		return new ErrorAuthenticationHandler();
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}

}
