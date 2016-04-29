package com.hospitalsearch.config.web;



import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import com.hospitalsearch.config.SpringRootConfig;

/**
 * Created by speedfire on 4/28/16.
 */
public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{SpringRootConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}



}
