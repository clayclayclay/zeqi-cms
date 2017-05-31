

/**
 * Project Name:zeqi-v2 
 * File Name:CustomHandlerExceptionResolver.java
 * Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author max
 * @date May 19, 2017 1:53:03 PM
 * @version 
 * @see
 * @since 
 */
 
package com.zeqi.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
  * ClassName: CustomHandlerExceptionResolver
  * Description: TODO
  * @author I337739
  * @version 
  * @see
  * @since 
  */

public class CustomHandlerExceptionResolver implements HandlerExceptionResolver{
	 /**
	  * Title: resolveException
	  * Description: TODO
	  * @param request
	  * @param response
	  * @param handler
	  * @param ex
	  * @return (describe the param)
	  * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	  */
	 
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ex.printStackTrace();
		return new ModelAndView("error");
	}
}