//
//
///**
// * Project Name:zeqi-v2 
// * File Name:ViewSolverConfig.java
// * Description: TODO
// * Copyright: Copyright (c) 2017 
// * Company:SAP
// * 
// * @author max
// * @date May 4, 2017 9:11:11 AM
// * @version 
// * @see
// * @since 
// */
// 
//package com.zeqi.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.ViewResolver;
//import org.thymeleaf.spring4.SpringTemplateEngine;
//import org.thymeleaf.spring4.view.ThymeleafViewResolver;
//import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
//import org.thymeleaf.templateresolver.TemplateResolver;
//
///**
//  * ClassName: ViewSolverConfig
//  * Description: TODO
//  * @author max
//  * @version 
//  * @see
//  * @since 
//  */
//
//@Configuration
//public class ViewSolverConfig {
//	
//	
//	@Bean
//	public TemplateResolver templateResolver() {
//		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
//		templateResolver.setPrefix("classpath:/view/html/");
//		templateResolver.setSuffix(".html");
//		templateResolver.setTemplateMode("HTML5");
//		return templateResolver;
//	}
//	
//	@Bean
//	public SpringTemplateEngine springTemplateEngine() {
//		SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
//		springTemplateEngine.setTemplateResolver(templateResolver());
//		return springTemplateEngine;
//	}
//	
//	@Bean
//	public ViewResolver viewResolver() {
//		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
//		thymeleafViewResolver.setTemplateEngine(springTemplateEngine());
//		thymeleafViewResolver.setOrder(1);
//		return thymeleafViewResolver;
//	}
//
//}
// 
