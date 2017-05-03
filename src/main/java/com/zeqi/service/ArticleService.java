package com.zeqi.service;

import com.zeqi.database.Article;
import com.zeqi.database.DocumentInfo;
import com.zeqi.entity.ArticleListInfoEntity;
import com.zeqi.json.BasicJson;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Max on 2016/10/14.
 */
public interface ArticleService {

    BasicJson addArticle(HttpServletRequest request);

    BasicJson deleteArticle(String[] articleId);

    BasicJson updateArticle(int id, HttpServletRequest request);

    BasicJson getArticle(int articleId);

    BasicJson getArticleList(String[] pageInfo, boolean isSpecific, String stuId);

    int getArticleNum(boolean isSpecific, String stuId);

}
