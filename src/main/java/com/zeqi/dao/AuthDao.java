package com.zeqi.dao;

import com.zeqi.database.StudentAccount;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Max on 2016/4/10.
 * 账户验证接口
 * 功能：账户验证Dao
 */

public interface AuthDao {

    /**
     *
     * @param request
     * @param username
     * @param password
     * @return
     */
    String login(HttpServletRequest request,String username,String password);
}




