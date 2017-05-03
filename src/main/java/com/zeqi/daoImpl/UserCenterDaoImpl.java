package com.zeqi.daoImpl;

import com.zeqi.dao.UserCenterDao;
import com.zeqi.database.Article;
import com.zeqi.database.BookLoan;
import com.zeqi.database.StudentInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Max on 2016/5/26.
 */
@Repository
public class UserCenterDaoImpl implements UserCenterDao {


    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();

    }


    //从数据库查询所有朋友
    @Override
    public List<StudentInfo> getFriends() {
        Session session = getSession();
        String hql = "FROM Student stu";
        List<StudentInfo> studentList = session.createQuery(hql).list();
        System.out.println("studentList的大小是:");
        System.out.println(studentList.size());
        return studentList;
    }

    //从数据库查询所发布的文章
    @Override
    public List<Article> getArticles(String userId) {
        Session session = getSession();
        String hql = "FROM Article article WHERE article.student.stu_id=:userId";
        List<Article> articleList = session.createQuery(hql).setString("userId", String.valueOf(userId)).list();
        return articleList;
    }



    //从数据库查询所借的书籍
    @Override
    public List<BookLoan> getBooksLoan(String userId) {
        Session session = getSession();
        String hql = "FROM BookLoan bookLoan WHERE bookLoan.stuId=:userId";
        List<BookLoan> bookLoan = session.createQuery(hql).setString("userId", String.valueOf(userId)).list();
        return bookLoan;
    }

    @Override
    public String getPosition(String userId) {



        return null;
    }
}
