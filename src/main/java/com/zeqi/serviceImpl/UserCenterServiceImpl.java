package com.zeqi.serviceImpl;

import com.zeqi.dao.AuthDao;
import com.zeqi.dao.BasicDao;
import com.zeqi.dao.UserCenterDao;
import com.zeqi.database.Article;
import com.zeqi.database.BookLoan;
import com.zeqi.database.StudentAccount;
import com.zeqi.database.StudentInfo;
import com.zeqi.dataconfig.UserResourceConfig;
import com.zeqi.json.BasicJson;
import com.zeqi.service.CommonService;
import com.zeqi.service.UserCenterService;
import com.zeqi.util.AmazonS3ConnectionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

/**
 * Created by Max on 2016/4/9.
 * 用户中心服务实现类
 * 用户登录、查看个人信息、更新个人信息、上传头像
 * 查看我的朋友、查看已借书籍、查看我所写的文章
 */

@Service
public class UserCenterServiceImpl implements UserCenterService {

    @Autowired
    private AuthDao authDao;
    @Autowired
    private BasicDao basicDao;
    @Autowired
    private UserCenterDao userCenterDao;
    @Autowired
    private UserResourceConfig userResourceConfig;
    @Autowired
    @Lazy
    private AmazonS3ConnectionUtil amazonS3ConnectionUtil;
    @Autowired
    private CommonService commonService;
    
    
    /**
     *用户登录验证
     * @param request 封装的request请求对象
     * @param username 用户输入的用户名
     * @param password 用户输入的密码
     * @return
     */
    @Override
    public BasicJson login(HttpServletRequest request,String username, String password) {
        BasicJson basicJson = new BasicJson(false);
        String code = authDao.login(request,username,password);
        if (code.equals("200")) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("登陆成功");
            String headPic = ((StudentInfo) request.getSession().getAttribute("student_info")).getHeadPic();
            basicJson.setJsonStr(headPic);
            request.getSession().setAttribute("is_login", true);
        }
        else if (code.equals("101")) {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode(code);
            basicJson.getErrMsg().setMessage("登陆失败");
        }
        else if (code.equals("102")) {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode(code);
            basicJson.getErrMsg().setMessage("登陆失败");
        }
        else {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode(code);
            basicJson.getErrMsg().setMessage("登陆失败");
        }
        return basicJson;
    }


//    /**
//     * 查看个人基本信息
//     * @param stuId 用户学号唯一标识
//     * @return 返回相应的用户基本信息的basicJson对象
//     */
//    @Override
//    public BasicJson getUserInfo(String stuId) {
//        BasicJson basicJson = new BasicJson(false);
//        StudentInfo StudentInfo = new StudentInfo();
//        StudentInfo = (StudentInfo)basicDao.get(StudentInfo,stuId);
//        if (StudentInfo == null) {
//            basicJson.setStatus(false);
//            basicJson.getErrMsg().setCode(code);
//            basicJson.getErrMsg().setMessage("获取个人基本信息失败");
//        }
//        else {
//            basicJson.setStatus(true);
//            code = 200;
//            basicJson.getErrMsg().setCode(code);
//            basicJson.getErrMsg().setMessage("获取个人基本信息成功");
//            basicJson.setJsonStr(StudentInfo);
//        }
//        return basicJson;
//    }

    /**
     * 作用：修改密码
     * @param json
     * @param request
     * @return
     */
    @Override
    public BasicJson updatePassword(Map<String, String> map, HttpServletRequest request) {
        BasicJson basicJson = new BasicJson(false);
        String oldPassword = map.get("oldPassword");
        String newPassword = map.get("newPassword");
        StudentAccount studentAccount = (StudentAccount) request.getSession().getAttribute("student_account");
        if (studentAccount.getPassword().equals(oldPassword)) {
            studentAccount.setPassword(newPassword);
            boolean isUpdate = basicDao.save(studentAccount);
            if (isUpdate) {
                basicJson.setStatus(true);
                basicJson.getErrMsg().setCode("200");
                basicJson.getErrMsg().setMessage("密码修改成功");
            }
            else {
                basicJson.getErrMsg().setCode("01004");
                basicJson.getErrMsg().setMessage("密码修改失败");
            }
        }
        else {
            basicJson.getErrMsg().setCode("01004");
            basicJson.getErrMsg().setMessage("原密码错误");
        }
        return basicJson;
    }


    /**
     * 更新个人基本信息
     * @param StudentInfo 更新之后的StudentInfo对象信息
     * @return 返回相应的用户基本信息的basicJson对象
     */

    public BasicJson updateUserInfo(StudentInfo StudentInfo)  {
        BasicJson basicJson = new BasicJson(false);
        int code;
        try {
            basicDao.save(StudentInfo);
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("更新个人基本信息成功");
            basicJson.setJsonStr(StudentInfo);
        } catch(Exception e) {
            e.printStackTrace();
//            System.out.println("抛错啦！！！");
            basicJson.getErrMsg().setCode("01003");
            basicJson.getErrMsg().setMessage("更新个人基本信息失败");
        }
        return basicJson;
    }

//    /**
//     * 获取我所写的文章列表
//     * @param request
//     * @return
//     */
    @Override
    public List<Article> getArticleList(HttpServletRequest request) {
//        String stuId = ((StudentInfo) request.getSession().getAttribute("student_info")).getStuId();
//        List<Article> articleList = (List<Article>) basicDao.getAllByForeignKey("Article", "stuId", stuId);
//        Article articleListInfoEntity;
//        StudentInfo studentInfo;
//        List<Article> articleListInfoEntityList = new ArrayList<Article>();
//        for (int i = 0; i < articleList.size(); i++) {
//            articleListInfoEntity = new Article();
//            studentInfo = (StudentInfo) basicDao.get(new StudentInfo(), articleList.get(i).getId());
//            articleListInfoEntity.setTitle(articleList.get(i).getTitle());
//            articleListInfoEntity.setAuthor(studentInfo.getName());
//            articleListInfoEntity.setArticleId(articleList.get(i).getId());
//            articleListInfoEntity.setWriteTime(articleList.get(i).getDateGmt());
//            articleListInfoEntityList.add(articleListInfoEntity);
//        }
//        return articleListInfoEntityList;
    	return null;
    }


    /**
     * 上传头像
     * @param multiRequest 文件上传请求对象multiRequest
     * @return 返回相应上传头像的basicjson对象
     */
    @Override
    public BasicJson uploadHeadPic(MultipartHttpServletRequest multiRequest, HttpServletResponse response){
        BasicJson basicJson = new BasicJson(false);
        Map<String,MultipartFile> fileMap = multiRequest.getFileMap();
        
        //边界检测，如果没有文件上传，则立即返回错误。
        if (fileMap == null) {
            System.out.println("没有文件上传");
            basicJson.getErrMsg().setCode("01005");
            return basicJson;
        }
        String keyName;
        if ((keyName = commonService.uploadObject(fileMap.get("avator"), amazonS3ConnectionUtil.getBucketAvatarName())) != null) {
	        StudentInfo StudentInfo = (StudentInfo)multiRequest.getSession().getAttribute("student_info");
			String headPic = userResourceConfig.getResourceConfig().get("imgPath") + keyName;
			StudentInfo.setHeadPic(headPic);
	        basicDao.save(StudentInfo);
	        basicJson.setStatus(true);
	        basicJson.getErrMsg().setCode("200");
	        basicJson.getErrMsg().setMessage("上传头像成功");
	        basicJson.setJsonStr(headPic);
        }
        else {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode("01005");
            basicJson.getErrMsg().setMessage("上传失败");
        }
        return basicJson;
    }

    @Override
    public BasicJson uploadBackgroundPic(MultipartHttpServletRequest multiRequest) {

        System.out.println("进入到service.uploadBackgroundPic方法中");

        BasicJson basicJson = new BasicJson(false);
        Map<String,MultipartFile> fileMap = multiRequest.getFileMap();

        //边界检测，如果没有文件上传，则立即返回错误。
        if (fileMap == null) {
            System.out.println("没有文件上传");
            basicJson.getErrMsg().setCode("01006");
            return basicJson;
        }
        
        String keyName;
        if ((keyName = commonService.uploadObject(fileMap.get("background"), amazonS3ConnectionUtil.getBucketBackgroundName())) != null) {
	        StudentInfo StudentInfo = (StudentInfo)multiRequest.getSession().getAttribute("student_info");
			String backgroundPic = userResourceConfig.getResourceConfig().get("backgroundImgPath") + keyName;
			StudentInfo.setBackgroundPic(backgroundPic);
	        basicDao.save(StudentInfo);
	        basicJson.setStatus(true);
	        basicJson.getErrMsg().setCode("200");
	        basicJson.getErrMsg().setMessage("背景图片更新成功");
	        basicJson.setJsonStr(backgroundPic);
        }
        else {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode("01006");
            basicJson.getErrMsg().setMessage("更新失败");
        }
        return basicJson;
    }


    //获取个人文章
    public BasicJson getStuArticles(String userId) {
//        BasicJson basicJson = new BasicJson(false);
//        StudentInfo StudentInfo = new StudentInfo();
//        try {
//            StudentInfo = (StudentInfo) basicDao.get(StudentInfo,userId);
//            Set<Article> articleSet = StudentInfo.getArticle();
//            System.out.println("SET 大小为：");
//            System.out.println(articleSet.size());
//            basicJson.setStatus(true);
//            List<Map<String,String>> articleList = new ArrayList<Map<String, String>>();
//            Iterator iterator = articleSet.iterator();
//            while (iterator.hasNext()) {
//                Map<String,String> map = new HashMap<String, String>();
//                Article article = (Article) iterator.next();
//                map.put("date_gmt", String.valueOf(article.getArticleDate()));
////                map.put("content_path", article.getContentPath());
//                map.put("title",article.getTitle());
//                articleList.add(map);
//            }
//            basicJson.setJsonStr(articleList);
//            basicJson.setStatus(true);
//            return basicJson;
//        } catch (Exception e) {
//            basicJson.getErrMsg().setMessage("获取文章异常");
//            return basicJson;
//        }
        return null;
    }

    //获取用户所写的文章
    @Override
    public List<Article> getArticles(String userId) {
        List<Article> ArticleList = userCenterDao.getArticles(userId);
        return ArticleList;
    }


    //获取用户所借书籍信息
    @Override
    public List<BookLoan> getBooksLoan(String userId) {
        List<BookLoan> bookLoanList = userCenterDao.getBooksLoan(userId);
        return bookLoanList;
    }

    @Override
    public Enum getPosition(String userId) {
        StudentInfo studentInfo;
        studentInfo = (StudentInfo) basicDao.get(StudentInfo.class, userId);
        return null;
    }


    /**
     * 获取用户所有朋友列表
     * @param userId
     * @return
     */
    @Override
    public List<StudentInfo> getFriends(String userId) {
        List<StudentInfo> StudentInfoListTemp = userCenterDao.getFriends();
        List<StudentInfo> StudentInfoList = new ArrayList<StudentInfo>();
        for (StudentInfo StudentInfo : StudentInfoListTemp) {
            if (StudentInfo.getStuId().equals(userId)) {
                continue;
            }
            else {
                StudentInfoList.add(StudentInfo);
            }
        }
        return StudentInfoList;
    }
}


