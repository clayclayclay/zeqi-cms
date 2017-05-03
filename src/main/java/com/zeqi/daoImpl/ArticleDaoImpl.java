package com.zeqi.daoImpl;

import com.zeqi.dao.ArticleDao;
import com.zeqi.database.Article;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Max on 2016/11/13.
 */
@Repository
public class ArticleDaoImpl implements ArticleDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }



    /**
     * 通过pageId来分页获取相应页面的文章列表
     * @param pageInfo
     */
    @Override
    public List<Article> getArticleListByPage(String[] pageInfo) {
        Session session = getSession();
        String pageNum = pageInfo[0];
        String perPageArticleNum = pageInfo[1];
        String hql = "FROM Article order by dateGmt desc";
        Query query = session.createQuery(hql);
        Integer page = Integer.valueOf(pageNum);
        Integer articleNum = Integer.valueOf(perPageArticleNum);
        List<Article> articleList = query.setFirstResult((page - 1) * articleNum).setMaxResults(articleNum).list();
        return articleList;
    }
}
