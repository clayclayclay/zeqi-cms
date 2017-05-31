

/**
 * Project Name:zeqi-v2 
 * File Name:MailTest.java
 * Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author max
 * @date May 30, 2017 7:58:12 PM
 * @version 
 * @see
 * @since 
 */
 
package com.zeqi;

import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
  * ClassName: MailTest
  * Description: TODO
  * @author I337739
  * @version 
  * @see
  * @since 
  */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = "/application.properties")  
public class MailTest {
	
	@Autowired
	private JavaMailSender emailSender;
	
	@Test
	public void mailTest() {
//		JavaMailSenderImpl emailSender =  new JavaMailSenderImpl();
//		emailSender.setHost("smtp.163.com");
//		emailSender.setUsername("13408032377");
//		emailSender.setHost("smtp.qq.com");
//		emailSender.setUsername("3030578339");
//		emailSender.setPassword("Qq84907952");
//		emailSender.setPort(25);
		
//		Properties props = emailSender.getJavaMailProperties();
//		props.put("mail.transport.protocal", "smtp");
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.socketFactory.port", 465);
//		props.put("mail.smtp.starttls.enabe", "true");
//		props.put("mail.debug", "true");
//		props.put("mail.smtp.ssl.enable","true");
		
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo("84907952@qq.com");
		smm.setSubject("spring boot mail test");
		smm.setText("it's just a test mail content");
		emailSender.send(smm);
	}
}
 
