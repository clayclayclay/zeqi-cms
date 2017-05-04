package com.zeqi.daoImpl;

import com.zeqi.dao.AuthDao;
import com.zeqi.database.StudentAccount;
import com.zeqi.database.StudentInfo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Max on 2016/4/10.
 * 账户验证实现类
 * 功能：账户验证Dao
 */

@Repository
public class AuthDaoImpl implements AuthDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    /**
     * 账户登录验证
     * @param request request请求对象，用以获得session来记录用户登录状态。
     * @param username 用户名
     * @param password 密码
     * @return 登陆情况的code码结果：
     * 200 登陆成功
     * 101 用户名错误
     * 102 密码错误
     */
    @Override
    public String login(HttpServletRequest request, String username,String password) {
        Session session = getSession();
        String code = "00111";
        try {
            String hql = "FROM StudentAccount s WHERE s.stuId=:username";
            List<StudentAccount> list = session.createQuery(hql).setString("username",username).list();
            if (list.size() >0) {
                if (list.get(0).getPassword().equals(password)) {
                	if (list.get(0).getStudentInfo().getHeadPic() == null) {
                		list.get(0).getStudentInfo().setHeadPic("/avatar/Pikachu.png");
                	}
                    request.getSession().setAttribute("student_info", list.get(0).getStudentInfo());
                    request.getSession().setAttribute("student_account", list.get(0));
                    request.getSession().setAttribute("is_login",true);
                    request.getSession().setMaxInactiveInterval(1200);
                    code = "200";
                }
                else {
                    code = "01002";
                }
            	
            	return "200";
            }
            else {
                code = "01001";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = "00111";
        } finally {
            return code;
        }
    }
}
