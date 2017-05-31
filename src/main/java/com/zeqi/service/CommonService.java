

/**
 * Project Name:zeqi-v2 
 * File Name:CommonService.java
 * Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author max
 * @date May 26, 2017 6:18:10 PM
 * @version 
 * @see
 * @since 
 */
 
package com.zeqi.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.zeqi.json.BasicJson;

/**
  * ClassName: CommonService
  * Description: TODO
  * @version 
  * @see
  * @since 
  */

public interface CommonService {
	
	public String uploadObject(MultipartFile multipartFile, String location);

	public BasicJson bugCommit(HttpServletRequest request);
	
}
 
