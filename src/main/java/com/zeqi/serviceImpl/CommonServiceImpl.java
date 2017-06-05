
/**
 * Project Name:zeqi-v2 
 * File Name:CommonServiceImpl.java
 * Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author max
 * @date May 26, 2017 6:19:14 PM
 * @version 
 * @see
 * @since 
 */

package com.zeqi.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.zeqi.dao.BasicDao;
import com.zeqi.database.StudentInfo;
import com.zeqi.database.WorkActivityInfo;
import com.zeqi.dto.WorkActivityEntityDTO;
import com.zeqi.json.BasicJson;
import com.zeqi.service.CommonService;
import com.zeqi.util.AmazonS3ConnectionUtil;
import com.zeqi.util.CommonUtil;
import com.zeqi.util.DateUtil;

/**
 * ClassName: CommonServiceImpl Description: TODO
 * 
 * @version
 * @see
 * @since
 */
@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	@Lazy
	private AmazonS3ConnectionUtil amazonS3ConnectionUtil;
	
	@Autowired
	private BasicDao basicDao;

	@Autowired
	private JavaMailSender emailSender;

	/**
	 * Title: uploadObject Description: TODO
	 * 
	 * @return (describe the param)
	 * @throws IOException
	 * @throws SdkClientException
	 * @throws AmazonServiceException
	 * @see com.zeqi.service.CommonService#uploadObject()
	 */

	@Override
	public String uploadObject(MultipartFile multipartFile, String location) {
		AmazonS3 s3 = amazonS3ConnectionUtil.getAmazonS3Client();
		String fileName = multipartFile.getOriginalFilename();
		String keyName = CommonUtil.nameToKey(fileName);
		System.out.println(keyName);
		ObjectMetadata ObjectMetadata = new ObjectMetadata();
		Map<String, String> userMetadata = new HashMap<String, String>();
		userMetadata.put("Name", fileName);
		userMetadata.put("key_name", keyName);
		ObjectMetadata.setUserMetadata(userMetadata);
		try {
			s3.putObject(location, keyName, multipartFile.getInputStream(), ObjectMetadata);
			return keyName;
		} catch (SdkClientException | IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Title: bugCommit Description: TODO
	 * 
	 * @param request
	 * @return (describe the param)
	 * @see com.zeqi.service.CommonService#bugCommit(javax.servlet.http.HttpServletRequest)
	 */

	@Override
	public BasicJson bugCommit(HttpServletRequest request) {
		BasicJson basicJson = new BasicJson(false);
		try {
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			mimeMessage.setContent(request.getParameter("content"), "text/html; charset=utf-8");
			helper.setTo("max.lv@sap.com");
			String subject = ((StudentInfo) request.getSession().getAttribute("student_info")).getName() + ">>>" + request.getParameter("title");
			helper.setSubject(subject);
			helper.setFrom("qq84907952@gmail.com");
			emailSender.send(mimeMessage);
			basicJson.setStatus(true);
			basicJson.getErrMsg().setMessage("已通知到程序猿啦！");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			basicJson.getErrMsg().setMessage("提交失败");
		}
		return basicJson;
	}

	
	 /**
	  * Title: recordActivity
	  * Description: TODO
	  * @param stuId
	  * @param operation
	  * @param entityId
	  * @return (describe the param)
	  * @see com.zeqi.service.CommonService#recordActivity(java.lang.String, java.lang.String, java.lang.String)
	  */
	 
	@Override
	public boolean recordActivity(StudentInfo studentInfo, String operation, String entityName) {
		WorkActivityInfo workActivityInfo = new WorkActivityInfo();
		workActivityInfo.setStudentInfo(studentInfo);
		workActivityInfo.setTitle(operation);
		String description = studentInfo.getName() + operation + entityName;
		workActivityInfo.setDescription(description);
		workActivityInfo.setTime(0);
		workActivityInfo.setRecordTime(DateUtil.getNowLongTime());
		if (basicDao.save(workActivityInfo)) {
			return true;
		}
		else {
			return false;
		}
	}

	
	 /**
	  * Title: queryRecordActivity
	  * Description: TODO
	  * @return (describe the param)
	  * @see com.zeqi.service.CommonService#queryRecordActivity()
	  */
	 
	@SuppressWarnings("unchecked")
	@Override
	public List<WorkActivityEntityDTO> queryRecordActivity() {
		List<WorkActivityInfo> workActivityInfoList = (List<WorkActivityInfo>) basicDao.getAll("WorkActivityInfo");
		List<WorkActivityEntityDTO> workActivityEntityDTOList = new ArrayList<WorkActivityEntityDTO>();
		for (WorkActivityInfo workActivityInfo : workActivityInfoList) {
			WorkActivityEntityDTO workActivityEntityDTO = new WorkActivityEntityDTO();
			workActivityEntityDTO.setTitle(workActivityInfo.getTitle());
			workActivityEntityDTO.setDescription(workActivityInfo.getDescription());
			workActivityEntityDTO.setRecordTime(DateUtil.longToString(workActivityInfo.getRecordTime()));
			workActivityEntityDTO.setStuName(workActivityInfo.getStudentInfo().getNickName());
			workActivityEntityDTOList.add(workActivityEntityDTO);
		}
		return workActivityEntityDTOList;
	}

	
	 /**
	  * Title: updateRecord
	  * Description: TODO
	  * @return (describe the param)
	  * @see com.zeqi.service.CommonService#updateRecord()
	  */
	 
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateRecord() {
		List<WorkActivityInfo> workActivityInfoList = (List<WorkActivityInfo>) basicDao.getAll("WorkActivityInfo");
		for (WorkActivityInfo workActivityInfo : workActivityInfoList) {
			if (workActivityInfo.getTime() == 1) {
				if (!basicDao.delete(workActivityInfo)) {
					return false;
				}
			}
			else if (workActivityInfo.getTime() == 0) {
				workActivityInfo.setTime(1);
				basicDao.save(workActivityInfo);
			}
		}
		return true;
	}
}
