package com.zeqi.daoImpl;

import com.zeqi.dao.ArticleDao;
import com.zeqi.database.Article;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Max on 2016/11/13.
 */
@Repository("articleDaoImpl")
public class ArticleDaoImpl implements ArticleDao{

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

	@Override
	public Object paginationQuery(int firstResult, int maxResults, Class<?> entityType) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(entityType);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResults);
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}

	@Override
	public Object paginationQuery(int firstResult, int maxResults, Class<?> entityType, String field, String id) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(entityType);
		criteria.add(Restrictions.eq(field, id));
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResults);
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}

}
