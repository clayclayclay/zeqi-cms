package com.zeqi.dao;

import com.zeqi.database.Notice;

import java.util.List;

/**
 * Created by Nanguoyu on 2016/5/31.
 */
public interface NoticeDao {

    List<Notice> getNoticesByType(String[] typeAndPageInfo);

    int getNoticesNumByType(int type);

}
