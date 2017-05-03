package com.zeqi.dao;

import com.zeqi.database.Article;

import java.util.List;

/**
 * Created by Max on 2016/11/13.
 */
public interface ArticleDao {

    public List<Article> getArticleListByPage(String[] pageInfo);
}
