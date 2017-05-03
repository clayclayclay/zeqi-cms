package com.zeqi.daoImpl;

import com.zeqi.dao.NoticeDao;
import com.zeqi.database.Notice;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Nanguoyu on 2016/5/31.
 */
@Repository
public class NoticeDaoImpl implements NoticeDao {


    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();

    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Notice> getNoticesByType(String[] typeAndPageInfo) {
        Session session = getSession();
        int type = Integer.valueOf(typeAndPageInfo[0]);
        int page = Integer.valueOf(typeAndPageInfo[1]);
        int entityNum = Integer.valueOf(typeAndPageInfo[2]);
        String hql = "FROM News WHERE type = " + type + "ORDER BY publishDate DESC";
        Query query = session.createQuery(hql);
        List<Notice> list = (List<Notice>) query.setFirstResult((page - 1) * entityNum).setMaxResults(entityNum).list();
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getNoticesNumByType(int type) {
        Session session = getSession();
        String hql = "FROM News WHERE type = " + type;
        Query query = session.createQuery(hql);
        return query.list().size();
    }
}
