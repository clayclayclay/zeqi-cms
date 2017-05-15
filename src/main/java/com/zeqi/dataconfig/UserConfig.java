
  
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
@ConfigurationProperties("user")
public class UserConfig
{

	private Map<String, List<String>> dataEnum;
	
	private Map<String, String> apiConfig;
	
	private Map<String, String> resourceConfig;

	public Map<String, List<String>> getDataEnum() {
	
		return dataEnum;
	}

	public void setDataEnum(Map<String, List<String>> dataEnum) {
		this.dataEnum = dataEnum;
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
 