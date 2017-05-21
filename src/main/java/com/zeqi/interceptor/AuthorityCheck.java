package com.zeqi.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by Max on 2016/10/21.
 */
public class AuthorityCheck extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("AuthorityCheck.class'preHandle() is called");
        System.out.println(request.getRequestURL());
        if (request.getSession().getAttribute("is_login") != null) {
            return true;
        }
        else {
            request.getSession().setAttribute("before_request", request.getRequestURL().toString());
            response.sendRedirect("/html/re_login.html");
            return false;
        }
    }
}
