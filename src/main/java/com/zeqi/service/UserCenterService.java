package com.zeqi.service;

import com.zeqi.database.Article;
import com.zeqi.database.BookLoan;
import com.zeqi.database.StudentInfo;
import com.zeqi.dto.ArticleIndexDTO;
import com.zeqi.database.StudentInfo;
import com.zeqi.json.BasicJson;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/4/9.
 * 用户中心服务接口
 * 用户登录、查看个人信息、更新个人信息、上传头像
 * 查看我的朋友、查看已借书籍、查看我所写的文章
 */

public interface UserCenterService {

    /**
     * 用户登录
     * @param request
     * @param username
     * @param password
     * @return
     */
    BasicJson login(HttpServletRequest request,String username,String password);


    /**
     * 上传头像
     * @param multiRequest 文件上传请求对象multiRequest
     * @return 返回相应上传头像的basicjson对象
     */
    BasicJson uploadHeadPic(MultipartHttpServletRequest multiRequest);

    /**
     * 修改个性背景图片
     * @param multiRequest 文件上传请求对象multiRequest
     * @return 返回相应上传头像的basicjson对象
     */
    BasicJson uploadBackgroundPic(MultipartHttpServletRequest multiRequest);


//    /**
//     * 查看个人基本信息
//     * @param stuId 用户学号唯一标识
//     * @return 返回相应的用户基本信息的basicJson对象
//     */
//    BasicJson getUserInfo(String stuId);


    /**
     * 修改密码
     * @param map
     * @return
     */
    BasicJson updatePassword(Map<String, String> map, HttpServletRequest request);

    /**
     * 更新个人基本信息
     * @param student 更新之后的Student对象信息
     * @return 返回相应的用户基本信息的basicJson对象
     */
    BasicJson updateUserInfo(StudentInfo student);


    /**
     * 获取我所写文章列表
     * @param request
     * @return
     */
    List<Article> getArticleList(HttpServletRequest request);

    /**
     * 查看我所写的文章
     * @param userId
     * @return
     */
    BasicJson getStuArticles(String userId);

    /**
     * 查看我的朋友
     * @param userId
     * @return
     */
    List<StudentInfo> getFriends(String userId);

    /**
     * 查看我所写的文章
     * @param userId
     * @return
     */
    List<Article> getArticles(String userId);

    /**
     * 查看已借书籍
     * @param userId
     * @return
     */
    List<BookLoan> getBooksLoan(String userId);

    /**
     * 查询职务（可能会删）
     * @param userId
     * @return
     */
    Enum getPosition(String userId);



}
