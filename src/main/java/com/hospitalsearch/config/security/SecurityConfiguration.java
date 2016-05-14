package com.hospitalsearch.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import javax.sql.DataSource;

/**
 * Created by speedfire on 4/28/16.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final Integer TIME = 21600;

	@Autowired
	@Qualifier("CustomUserDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	private DataSource dataSource;


	@Autowired
	private CustomAuthenticationHandler customHandler;

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
					.antMatchers("/", "/home").permitAll()
					.antMatchers("/admin/**").access("hasRole('ADMIN')")
					.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('MANAGER')")
					.and()
				.formLogin()
					.loginPage("/login")
					.usernameParameter("email")
					.passwordParameter("password")
					.successHandler(customHandler)
					.and().csrf()
					.and()
				.logout()
					.logoutSuccessUrl("/?logout")
					.invalidateHttpSession(true)
					.and()
				.exceptionHandling()
					.accessDeniedPage("/Access_Denied")
					.and()
				.rememberMe()
					.rememberMeParameter("remember-me")
					.tokenRepository(persistentTokenRepository())
					.tokenValiditySeconds(TIME);
				/*	.and()
				.sessionManagement()
					.invalidSessionUrl("/welcome")
					.maximumSessions(1);*/
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


	//remember me
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		tokenRepositoryImpl.setDataSource(dataSource);
		return tokenRepositoryImpl;
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect(){
		return new SpringSecurityDialect();
	}
}
