package com.zeqi.service;

import com.zeqi.database.Notice;
import com.zeqi.json.BasicJson;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Nanguoyu on 2016/5/31.
 */
public interface NoticeService {


    BasicJson addNotice(HttpServletRequest request);

    BasicJson deleteNotice(String[] noticesId);

    BasicJson updateNotice(int id, HttpServletRequest request);

    BasicJson getNoticesByType(String[] typeAndPageInfo);

    BasicJson getNotice(int id);

    int getNoticesNumByType(String type);

}
