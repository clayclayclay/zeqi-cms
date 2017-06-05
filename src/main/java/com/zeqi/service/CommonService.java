

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


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.zeqi.database.StudentInfo;
import com.zeqi.dto.WorkActivityEntityDTO;
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
	
	public boolean recordActivity(StudentInfo studentInfo, String operation, String entityName);
	
	public List<WorkActivityEntityDTO> queryRecordActivity();
	
	public boolean updateRecord();
	
}
 
