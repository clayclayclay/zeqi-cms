package com.zeqi.dao;

import com.zeqi.database.Article;
import com.zeqi.database.BookLoan;
import com.zeqi.database.StudentInfo;

import java.util.List;

/**
 * Created by Nanguoyu on 2016/5/26.
 */
public interface UserCenterDao {

    List<StudentInfo> getFriends();

    List<Article> getArticles(String userId);

    List<BookLoan> getBooksLoan(String userId);

    String getPosition(String userId);

}

