package com.hospitalseeker;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.hospitalsearch.service.UserService;


@Configuration
public class TestConfig {
	@Bean
	@Lazy
	private UserService service(){
		return Mockito.mock(UserService.class);
	}
}
