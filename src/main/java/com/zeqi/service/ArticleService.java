package com.zeqi.service;

import com.zeqi.dto.ArticleEntityDTO;
import com.zeqi.json.BasicJson;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Max on 2016/10/14.
 */
public interface ArticleService {

    BasicJson addArticle(HttpServletRequest request);

    BasicJson deleteArticle(String[] articleId);

    BasicJson updateArticle(int id, HttpServletRequest request);

    ArticleEntityDTO getArticle(int articleId);

    Map<String, Object> getArticleList(String page);
    
    Map<String, Object> getArticleList(String page, String stuId);
    
}
