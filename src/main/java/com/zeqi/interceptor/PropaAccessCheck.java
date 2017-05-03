package com.zeqi.interceptor;

import com.zeqi.database.StudentInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by Max on 2016/10/21.
 */
public class PropaAccessCheck extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("PropaAccessCheck.class'preHandle() is called");
        StudentInfo studentInfo = (StudentInfo) request.getSession().getAttribute("student_info");
        if (studentInfo.getPosition().equals("对外宣传部成员") || studentInfo.getPosition().equals("对外宣传部部长")) {
            return true;
        }
        else {
            response.sendRedirect("/view/html/no_access.html");
            return false;
        }
    }
}
