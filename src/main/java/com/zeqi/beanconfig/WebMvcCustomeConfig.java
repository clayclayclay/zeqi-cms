
/**
 * Project Name:zeqi-v2 
 * File Name:InterceptorConfig.java
 * Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author max
 * @date May 18, 2017 2:56:30 PM
 * @version 
 * @see
 * @since 
 */

package com.zeqi.beanconfig;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.zeqi.exception.CustomHandlerExceptionResolver;
import com.zeqi.interceptor.AuthorityCheck;

/**
 * ClassName: InterceptorConfig Description: TODO
 * 
 * @author I337739
 * @version
 * @see
 * @since
 */

@Configuration
public class WebMvcCustomeConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthorityCheck()).addPathPatterns("/web/**").excludePathPatterns("/web/login",
				"/web/article/**", "/web/guy/reLogin");
	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(new CustomHandlerExceptionResolver());
	}
	
	
	
	
}
