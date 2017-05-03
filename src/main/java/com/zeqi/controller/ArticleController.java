package com.zeqi.controller;

import com.zeqi.database.Article;
import com.zeqi.database.StudentInfo;
import com.zeqi.json.BasicJson;
import com.zeqi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Max on 2016/5/31.
 */

@RestController
@RequestMapping("/web")
public class ArticleController {


    @Autowired
    private ArticleService articleService;

    /**
     * 作用：发布文章
     * @param request
     */
    @RequestMapping(value = "/article", method = RequestMethod.POST)
    public BasicJson addArticle(HttpServletRequest request) {
        BasicJson basicJson;
        basicJson = articleService.addArticle(request);
        return basicJson;
    }

    /**
     * 作用：删除文章
     * @param request
     */
    @RequestMapping(value = "/article/{id}", method = RequestMethod.DELETE)
    public BasicJson deleteArticle(@PathVariable String id, HttpServletRequest request) {
        BasicJson basicJson;
        String[] articleId = id.split("&");
        basicJson = articleService.deleteArticle(articleId);
        return basicJson;
    }


    /**
     * 作用：修改文章
     * @param request
     */
    @RequestMapping(value = "/article/{id}", method = RequestMethod.PUT)
    public BasicJson updateArticle(@PathVariable int id, HttpServletRequest request) {
        BasicJson basicJson;
        basicJson = articleService.updateArticle(id, request);
        return basicJson;
    }

    /**
     * 作用：读取文章
     * @param request
     */
    @RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
    public BasicJson getArticle(@PathVariable int id, HttpServletRequest request) {
        BasicJson basicJson;
        basicJson = articleService.getArticle(id);
        return basicJson;
    }


    /**
     * 作用：获取文章列表
     * @return
     */
    @RequestMapping(value = "/articles/{page}", method = RequestMethod.GET)
    public BasicJson getArticleList(@PathVariable String page) {
        BasicJson basicJson;
        String[] pageInfo = page.split("&");
        basicJson = articleService.getArticleList(pageInfo, false, null);
        return basicJson;
    }

    /**
     * 作用：获取当前用户所写文章列表
     * @return
     */
    @RequestMapping(value = "/articles/guy/{page}", method = RequestMethod.GET)
    public BasicJson getArticleListInGuy(@PathVariable String page, HttpServletRequest request) {
        BasicJson basicJson;
        String[] pageInfo = page.split("&");
        basicJson = articleService.getArticleList(pageInfo, true, ((StudentInfo) request.getSession().getAttribute("student_info")).getStuId());
        return basicJson;
    }
}
