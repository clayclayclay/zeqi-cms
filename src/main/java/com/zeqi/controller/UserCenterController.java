package com.zeqi.controller;

import com.zeqi.database.BookLoan;
import com.zeqi.database.StudentAccount;
import com.zeqi.database.StudentInfo;
import com.zeqi.dataconfig.UserConfig;
import com.zeqi.dto.ArticleIndexDTO;
import com.zeqi.json.BasicJson;
import com.zeqi.service.UserCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/4/9. 用户中心控制类 功能包括： 用户登录、查看个人信息、更新个人信息、修改头像、修改个性背景图片
 * 查看我的文章、查看我的朋友、查看已借书籍、上传相应文档、删除相应文档、修改账户密码
 */

// @RestController
@Controller
@RequestMapping("/web")
public class UserCenterController {

	@Autowired
	private UserCenterService userCenterService;

	@Autowired
	private UserConfig userConfig;

	// final static Logger logger =
	// Logger.getLogger(UserCenterController.class);

	/**
	 * 功能：用户登录
	 * 
	 * @param request
	 *            请求对象request,获取通过POST传递的username，password
	 * @return 返回相应登陆情况的json字符串
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public BasicJson login(HttpServletRequest request) {
		BasicJson basicJson;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		basicJson = userCenterService.login(request, username, password);
		return basicJson;
	}

	/**
	 * 功能：进入个人中心首页(iframe1页面)
	 * 
	 * @return 返回视图(view)和数据(Model)对象 视图： user_module/user_center页面 数据： 用户个人基本信息
	 */
	@RequestMapping(value = "/guy", method = RequestMethod.GET)
	public String getInfo(Map<String, Object> model, HttpServletRequest request) {
		StudentInfo studentInfo = (StudentInfo) request.getSession().getAttribute("student_info");
		model.put("studentInfo", studentInfo);
		System.out.println(studentInfo.getPosition());
		return "user/user_center";
	}

	/**
	 * 功能：查看个人基本信息
	 * 
	 * @param request
	 *            request请求对象
	 * @return 返回视图(view)和数据(Model)对象 视图： user_module/user_info页面 数据： 用户个人基本信息
	 */
	@RequestMapping(value = "/guy/basicInfo", method = RequestMethod.GET)
	public String getInfo1(Map<String, Object> model, HttpServletRequest request) {
		StudentInfo studentInfo = (StudentInfo) request.getSession().getAttribute("student_info");
		model.put("studentInfo", studentInfo);
		return "user/user_info";
	}

	/**
	 * 作用：在修改个人信息之前展示个人信息，这仅仅是一个页面的跳转
	 * 
	 * @param request
	 *            request请求对象
	 * @return 返回视图(view)和数据(Model)对象 视图： user_module/user_info_modify页面 数据：
	 *         用户个人基本信息
	 */
	@RequestMapping(value = "/guy/personInfo", method = RequestMethod.GET)
	public String getPersonInfo(Map<String, Object> model, HttpServletRequest request) {
		StudentInfo studentInfo = (StudentInfo) request.getSession().getAttribute("student_info");
		Map<String, List<String>> dataConfig = new HashMap<String, List<String>>();
		dataConfig.put("grade", userConfig.getDataEnum().get("grade"));
		dataConfig.put("major", userConfig.getDataEnum().get("major"));
		dataConfig.put("position", userConfig.getDataEnum().get("position"));
		model.put("studentInfo", studentInfo);
		model.put("userConfig", dataConfig);
		return "user/user_info_modify";
	}

	/**
	 * 作用：更新个人基本信息
	 * 
	 * @param request
	 *            request请求对象
	 * @return
	 */
	@RequestMapping(value = "/guy", method = RequestMethod.PUT)
	@ResponseBody
	public BasicJson updateUserInfo(@RequestBody StudentInfo student, HttpServletRequest request) {
		BasicJson basicJson;
		StudentInfo studentInfo = (StudentInfo) request.getSession().getAttribute("student_info");
		student.setStuId(studentInfo.getStuId());
		student.setBackgroundPic(studentInfo.getBackgroundPic());
		student.setHeadPic(studentInfo.getHeadPic());
		basicJson = userCenterService.updateUserInfo(student);
		if (basicJson.getErrMsg().getCode().equals("200")) {
			request.getSession().setAttribute("student_info", basicJson.getJsonStr());
		}
		return basicJson;
	}

	/**
	 * 作用：用户上传头像跳转控制,这仅仅是一个页面的跳转
	 * 
	 * @param request
	 *            请求对象request
	 * @return 返回视图(view)和数据(Model)对象 视图： user_module/user_head_img_modify页面 数据：
	 *         头像地址信息
	 */
	@RequestMapping(value = "/guy/headPicView", method = RequestMethod.GET)
	public String DispatchHeadic(HttpServletRequest request) {
		return "user/user_avatar";
	}

	/**
	 * 作用：用户上传头像 （暂时文件上传不支持PUT方式）
	 * 
	 * @param multipartHttpServletRequest
	 *            文件上传请求对象multiRequest
	 * @return 返回相应上传头像的json字符串
	 */
	@RequestMapping(value = "/guy/headPic", method = RequestMethod.POST)
	@ResponseBody
	public BasicJson uploadHeadPic(MultipartHttpServletRequest multipartHttpServletRequest, HttpServletResponse response) {
		BasicJson basicJson;
		basicJson = userCenterService.uploadHeadPic(multipartHttpServletRequest, response);
		return basicJson;
	}

	/**
	 * 作用：修改个性背景图片
	 * 
	 * @param multipartHttpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/guy/backgroundPic", method = RequestMethod.POST)
	@ResponseBody
	public BasicJson uploadBackgroundPic(MultipartHttpServletRequest multipartHttpServletRequest) {
		System.out.println("Controller's uploadBackgroundPic() is called");
		BasicJson basicJson;
		basicJson = userCenterService.uploadBackgroundPic(multipartHttpServletRequest);
		return basicJson;
	}

	/**
	 * 作用：跳转到查看 “我所写的文章列表” 页面，这仅仅是一个页面的跳转
	 * 
	 * @return
	 */
	@RequestMapping("/guy/myArticles")
	public ModelAndView getStuArticles() {
		ModelAndView modelAndView = new ModelAndView("article_module/article_write");
		return modelAndView;
	}

	/**
	 * 作用：查看我的朋友列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/guy/myFriends", method = RequestMethod.GET)
	public ModelAndView getFriends(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("myfriends");
		String userId = ((StudentAccount) request.getSession().getAttribute("student_account")).getStuId();
		List<StudentInfo> studentInfoList = userCenterService.getFriends(userId);
		modelAndView.addObject("myFriendList", studentInfoList);
		return modelAndView;
	}

	/**
	 * 作用：返回尚未完成的页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sorry", method = RequestMethod.GET)
	public ModelAndView turnUnfinishedPage() {
		ModelAndView modelAndView = new ModelAndView("sorry");
		return modelAndView;
	}

	/**
	 * 作用：登出账户
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exit", method = RequestMethod.GET)
	public void exit(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("student_info");
		request.getSession().removeAttribute("student_account");
		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 作用：修改密码
	 * 
	 * @param json
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/guy/password", method = RequestMethod.PUT)
	@ResponseBody
	public BasicJson updatePassword(@RequestBody Map<String, String> map, HttpServletRequest request) {
		BasicJson basicJson = null;
		basicJson = userCenterService.updatePassword(map, request);
		return basicJson;
	}

	/**
	 * 作用：重新登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/guy/reLogin", method = RequestMethod.POST)
	public ModelAndView reLogin(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		BasicJson basicJson;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		basicJson = userCenterService.login(request, username, password);
		if (request.getSession().getAttribute("before_request") != null) {
			if (basicJson.isStatus()) {
				modelAndView.setViewName("redirect:" + (String) request.getSession().getAttribute("before_request"));
				System.out.println((String) request.getSession().getAttribute("before_request"));
				System.out.println("sebdRedirect");
			} else {
				request.getSession().setAttribute("is_re_login", false);
				request.getSession().setAttribute("login_info", "用户名或密码错误");
				modelAndView.setViewName("/reLogin");
				System.out.println("relogin is error");
			}
		}
		return modelAndView;
	}

	/**
	 * 作用：跳转到重新登陆页面
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/guy/reLogin", method = RequestMethod.GET)
	public void relogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BasicJson basicJson = login(request);
		if (basicJson.isStatus()) {
			if ((String) request.getSession().getAttribute("before_request") != null) {
				response.sendRedirect((String) request.getSession().getAttribute("before_request"));
			} else {
				response.sendRedirect("/web/guy");
			}
		}
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	@ResponseBody
	public void test(MultipartHttpServletRequest multipartHttpServletRequest) {
		MultipartFile file = multipartHttpServletRequest.getFile("croppedImage");
		System.out.println(file.getOriginalFilename());
	}
	
	@RequestMapping("/s")
	public String s(Map<String, Object> model) {
		return "index";
	}
}
