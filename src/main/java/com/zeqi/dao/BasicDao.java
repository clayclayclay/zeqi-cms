package com.zeqi.dao;


import java.io.Serializable;

/**
 * Created by Nanguoyu on 2016/4/10.
 */
public interface BasicDao {

    Object get(Object obj,Serializable id);

    Object getAll(String table);

    Object getAllByForeignKey(String table, String foreignKey, Serializable id);

    Object getAllByPage(String table, String[] pageInfo, boolean isSort, String sortKey);

    Object getAllByPageById(String table, String[] pageInfo, boolean isSort, String sortKey, String idName, Serializable id);

    boolean save(Object obj);

    boolean delete(String[] id, Object obj);

    boolean deleteById(String table, String key, String[] id);

}

