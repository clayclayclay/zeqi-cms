package com.zeqi.controller;

import com.zeqi.database.StudentInfo;
import com.zeqi.dto.ArticleEntityDTO;
import com.zeqi.json.BasicJson;
import com.zeqi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * Created by Max on 2016/5/31.
 */

@Controller
@RequestMapping("/web")
public class ArticleController {


    @Autowired
    private ArticleService articleService;

    /**
     * 作用：发布文章
     * @param request
     */
    @RequestMapping(value = "/article", method = RequestMethod.POST)
    @ResponseBody
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
    public String getArticle(Map<String, Object> model, @PathVariable int id, HttpServletRequest request) {
        ArticleEntityDTO articleEntityDTO = articleService.getArticle(id);
        model.put("article", articleEntityDTO);
        return "article/article";
    }


    /**
     * 作用：获取文章列表
     * @return
     */
    @RequestMapping(value = "/articles/{page}", method = RequestMethod.GET)
    public String getArticleList(Map<String, Object> model, @PathVariable String page) {
    	Map<String, Object> map;
    	map = articleService.getArticleList(page);
        model.put("articleList", map.get("articleDTOList"));
        model.put("articleConfig", map.get("articleConfig"));
        return "article/article_list";
    }

    /**
     * 作用：获取当前用户所写文章列表
     * @return
     */
    @RequestMapping(value = "/articles/guy/{page}", method = RequestMethod.GET)
    public String getArticleListInGuy(Map<String, Object> model, @PathVariable String page, HttpServletRequest request) {
    	Map<String, Object> map;
    	map = articleService.getArticleList(page, ((StudentInfo) request.getSession().getAttribute("student_info")).getStuId());
        model.put("articleList", map.get("articleDTOList"));
        model.put("articleConfig", map.get("articleConfig"));
        return "article/article_list_guy";
    }
}
