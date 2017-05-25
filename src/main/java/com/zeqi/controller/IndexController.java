package com.zeqi.controller;

import com.zeqi.database.StudentInfo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Max on 2016/11/24.
 */
@Controller
public class IndexController {


    @RequestMapping(value = "/", method=RequestMethod.GET)
    public String index(HttpServletRequest request, Map<String, Object> model) {
		if (request.getSession().getAttribute("is_login") != null) {
			model.put("isLogin", true);
			model.put("user_avatar", ((StudentInfo)request.getSession().getAttribute("student_info")).getHeadPic());
		}
		else {
			model.put("isLogin", false);
		}
		return "index";
    }
    
    @RequestMapping(value = "/exception", method=RequestMethod.GET)
    public void exception(HttpServletRequest request, Map<String, Object> model) throws Exception {
    	throw new Exception();
    }
    
    
    @RequestMapping("/test")
    @ResponseBody
    public String test() {
    	return "test";
    }
}
