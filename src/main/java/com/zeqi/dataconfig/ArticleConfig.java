
  
 /**
  * Project Name:spring-boot-Thymeleaf-demo-01 
  * File Name:UserConfig.java <br/><br/>  
  * Description: TODO
  * Copyright: Copyright (c) 2017 
  * Company:SAP
  * 
  * @author SAP
  * @date May 8, 2017 11:29:22 AM
  * @version 
  * @see
  * @since 
  */
  package com.zeqi.dataconfig;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
  * ClassName: UserConfig <br/><br/> 
  * Description: TODO
  * @author SAP
  * @version 
  * @see
  * @since 
  */
@Component
@ConfigurationProperties("article")
public class ArticleConfig
{

	
	private Map<String, String> paging;
	private Map<String, String> apiConfig;

	public Map<String, String> getPaging() {
	
		return paging;
	}

	public void setPaging(Map<String, String> paging) {
		this.paging = paging;
	}

	public Map<String, String> getApiConfig() {
	
		return apiConfig;
	}

	public void setApiConfig(Map<String, String> apiConfig) {
		this.apiConfig = apiConfig;
	}
	
	
	
 
    
}
 