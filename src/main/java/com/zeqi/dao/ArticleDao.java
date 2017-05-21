package com.zeqi.dao;

import com.zeqi.database.Notice;

import java.util.List;

/**
 * Created by Nanguoyu on 2016/5/31.
 */
public interface ArticleDao {

	public Object paginationQuery(int firstResult, int maxResults, Class<?> entityType);
	
	public Object paginationQuery(int firstResult, int maxResults, Class<?> entityType,  String field, String id);
}
