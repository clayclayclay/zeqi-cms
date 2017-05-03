
/**
 * Project Name:zeqi-v2 
 * File Name:JpaConfig.java
 * Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author max
 * @date May 3, 2017 6:45:50 PM
 * @version 
 * @see
 * @since 
 */

package com.zeqi.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * ClassName: JpaConfig Description: TODO
 * 
 * @author max
 * @version
 * @see
 * @since
 */

@Configuration
public class JpaConfig {

	@Autowired
	private Environment env;

	@Bean
	public ComboPooledDataSource comboPooledDataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setMinPoolSize(Integer.parseInt(env.getProperty("spring.jpa.properties.hibernate.c3p0.min_size")));
		dataSource.setMaxPoolSize(Integer.parseInt(env.getProperty("spring.jpa.properties.hibernate.c3p0.max_size")));
		dataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));
		dataSource.setUser(env.getProperty("spring.datasource.username"));
		dataSource.setDriverClass(env.getProperty("spring.datasource.driver-class-name"));
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() throws PropertyVetoException {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(comboPooledDataSource());
		// sessionFactoryBean.setPackagesToScan(env.getProperty("entitymanager.packagesToScan"));
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
		hibernateProperties.put("hibernate.show_sql", env.getProperty("spring.jpa.properties.hibernate.show-sql"));
		hibernateProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.properties.hibernate.ddl-auto"));
		sessionFactoryBean.setHibernateProperties(hibernateProperties);
		return sessionFactoryBean;
	}

	@Bean
	public HibernateTransactionManager transactionManager() throws PropertyVetoException {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
