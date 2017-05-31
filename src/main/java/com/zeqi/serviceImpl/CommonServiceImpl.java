
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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.zeqi.database.StudentInfo;
import com.zeqi.json.BasicJson;
import com.zeqi.service.CommonService;
import com.zeqi.util.AmazonS3ConnectionUtil;
import com.zeqi.util.CommonUtil;

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
			SimpleMailMessage smm = new SimpleMailMessage();
			smm.setFrom("qq84907952@gmail.com");
			smm.setTo("max.lv@sap.com");
			String subject = ((StudentInfo) request.getSession().getAttribute("student_info")).getName() + ">>>" + request.getParameter("title");
			System.out.println(subject);
			smm.setSubject(subject);
			smm.setText(request.getParameter("content"));
			emailSender.send(smm);
			System.out.println("done");
			basicJson.setStatus(true);
			basicJson.getErrMsg().setMessage("已通知到程序猿啦！");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			basicJson.getErrMsg().setMessage("提交失败");
		}
		return basicJson;
	}
}
