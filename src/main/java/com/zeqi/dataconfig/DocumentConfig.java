
  
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

import java.util.Map;

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
@ConfigurationProperties("document")
public class DocumentConfig
{
	private Map<String ,String> paging;
	private Map<String, String> apiConfig;
	private Map<String, String> resourceConfig;
	
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
	public Map<String, String> getResourceConfig() {
	
		return resourceConfig;
	}
	public void setResourceConfig(Map<String, String> resourceConfig) {
		this.resourceConfig = resourceConfig;
	}
	
	
	
 
}
 