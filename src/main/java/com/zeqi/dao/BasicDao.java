package com.zeqi.dao;


import java.io.Serializable;

/**
 * Created by Nanguoyu on 2016/4/10.
 */
public interface BasicDao {

    Object get(Class<?> entityType,Serializable id);

    Object getAll(String table);

    Object getAllByForeignKey(String table, String foreignKey, Serializable id);

    Object getAllByPage(String table, String page, String num, boolean isSort, String sortKey);

    Object getAllByPageById(String table, String page, String num, boolean isSort, String sortKey, String idName, Serializable id);

    boolean save(Object obj);

    boolean delete(String[] id, Class<?> entityType);

    boolean deleteById(String table, String key, String[] id);
    
    Object paginationQuery(int firstResult, int maxResults, Class<?> entityType);
    
    Object paginationQuery(int firstResult, int maxResults, Class<?> entityType, String field, String id);
    
    long getTotalCount(Class<?> entityType);
    
    long getTotalCount(String field, String stuId, Class<?> entityType);
    

}

