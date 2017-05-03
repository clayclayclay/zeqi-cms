package com.zeqi.daoImpl;

import com.zeqi.dao.BasicDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

/**
 * Created by Max on 2016/4/11.
 */
@Repository
@Transactional
public class BasicDaoImpl implements BasicDao {


    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    /**
     * 获取一条记录（实）体信息
     *
     * @param obj
     * @param id
     * @return
     */
    @Override
    public Object get(Object obj, Serializable id) {
        Session session = getSession();
        System.out.println("获得的id为：" + id);
        Object object;
        try {
            if ((object = session.get(obj.getClass(), id)) == null) {
                System.out.println("BasicDaoImpl.get()方法，没有取得任何实体");
            } else {
                System.out.println("BasicDaoImpl.get()方法，取得了实体");
            }
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到该表的所有记录
     *
     * @param table (为持久类名)
     * @return
     */
    @Override
    public Object getAll(String table) {
        Session session = getSession();
        Query query;
        try {
            query = session.createQuery("from " + table);
            List<Object> objectList = query.list();
            System.out.println("the " + table + "List'size is :" + objectList.size());
            return objectList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过外键来获取相关数据
     *
     * @param table
     * @param id
     * @return
     */
    @Override
    public Object getAllByForeignKey(String table, String foreignKey, Serializable id) {
        Session session = getSession();
        Query query;
        try {
            String str = "from " + table + " WHERE " + foreignKey + "=" + id;
            System.out.println(str);
            if (id instanceof Integer) {
                System.out.println("it's Integer");
                query = session.createQuery("from " + table + " WHERE " + foreignKey + "=" + id + "");
            } else {
                query = session.createQuery("from " + table + " WHERE " + foreignKey + "='" + id + "'");
            }

            List<Object> objectList = query.list();
            System.out.println("the " + table + "List'size is :" + objectList.size());
            return objectList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param table
     * @param pageInfo
     * @param isSort
     * @param sortKey
     * @return
     */
    @Override
    public Object getAllByPage(String table, String[] pageInfo, boolean isSort, String sortKey) {
        Session session = getSession();
        Integer page = Integer.valueOf(pageInfo[0]);
        Integer entityNum = Integer.valueOf(pageInfo[1]);
        List<Object> list;
        if (isSort) {
            if (sortKey != null) {
                String hql = "FROM " + table + " ORDER BY " + sortKey + " DESC";
                Query query = session.createQuery(hql);
                list = query.setFirstResult((page - 1) * entityNum).setMaxResults(entityNum).list();
            } else {
                return null;
            }
        } else {
            String hql = "FROM " + table;
            Query query = session.createQuery(hql);
            list = query.setFirstResult((page - 1) * entityNum).setMaxResults(entityNum).list();
        }
        return list;
    }

    @Override
    public Object getAllByPageById(String table, String[] pageInfo, boolean isSort, String sortKey, String idName, Serializable id) {
        Session session = getSession();
        Integer page = Integer.valueOf(pageInfo[0]);
        Integer entityNum = Integer.valueOf(pageInfo[1]);
        List<Object> list;
        if (isSort) {
            if (sortKey != null) {
                String hql = "FROM " + table + " WHERE " + idName + " = " + id + " ORDER BY " + sortKey + " DESC";
                Query query = session.createQuery(hql);
                list = query.setFirstResult((page - 1) * entityNum).setMaxResults(entityNum).list();
            } else {
                return null;
            }
        } else {
            String hql = "FROM " + table;
            Query query = session.createQuery(hql);
            list = query.setFirstResult((page - 1) * entityNum).setMaxResults(entityNum).list();
        }
        return list;
    }


    /**
     * 插入一条记录
     *
     * @param obj
     * @return
     */
    @Override
    public boolean save(Object obj) {
        Session session = getSession();
        Transaction transaction = session.getTransaction();
//        Transaction transaction = session.beginTransaction();
        try {
            transaction.begin();
            System.out.println("~~~~~");
            session.saveOrUpdate(obj);
            session.flush();
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除记录
     *
     * @param id 编号
     * @return
     */
    @Override
    public boolean delete(String[] id, Object obj) {

        Session session = getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            for (int i = 0; i < id.length; i++) {
                Object entity = session.get(obj.getClass(), Integer.valueOf(id[i]));
                session.delete(entity);
                session.flush();
            }
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteById(String table, String key, String[] id) {
        Session session = getSession();
        Transaction transaction = session.getTransaction();
        try {
//            transaction.begin();
            Query query;
            for (int i = 0; i < id.length; i++) {
                String hql = "DELETE FROM " + table + " WHERE " + key + "=:id";
                System.out.println(hql);
                query = session.createQuery(hql);
                query.setParameter("id", Integer.valueOf(id[i]));
                query.executeUpdate();
            }
            session.flush();
//            transaction.commit();
            return true;
        } catch (Exception e) {
//            transaction.rollback();
            e.printStackTrace();
            return false;
        }

    }

}



