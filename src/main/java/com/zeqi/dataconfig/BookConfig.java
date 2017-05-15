
  
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
@ConfigurationProperties("book")
public class BookConfig
{

    private String perBookNum;

	public String getPerBookNum() {
	
		return perBookNum;
	}

	public void setPerBookNum(String perBookNum) {
		this.perBookNum = perBookNum;
	}
 
    
	 
 
    
}
 