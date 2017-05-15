package com.zeqi.serviceImpl;

import com.zeqi.dao.BasicDao;
import com.zeqi.dao.NoticeDao;
import com.zeqi.database.Notice;
import com.zeqi.json.BasicJson;
import com.zeqi.json.EntityJson;
import com.zeqi.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Nanguoyu on 2016/5/31.
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    private BasicDao basicDao;


    /**
     * 作用：发布信息
     * @param request
     * @return
     */
    @Override
    public BasicJson addNotice(HttpServletRequest request) {
        BasicJson basicJson = new BasicJson(false);
        Notice notice = new Notice();
        notice.setTitle(request.getParameter("title"));
        notice.setContent(request.getParameter("content"));
        notice.setType(Integer.valueOf(request.getParameter("type")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        notice.setPublishDate(sdf.format(new Date()));
        notice.setReadCount(1);
        if (basicDao.save(notice)) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("发布成功");
            basicJson.setJsonStr(notice.getNewsId());
        }
        else {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode("06001");
            basicJson.getErrMsg().setMessage("发布失败");
        }
        return basicJson;
    }

    /**
     * 作用：删除信息
     * @param noticesId
     * @return
     */
    @Override
    public BasicJson deleteNotice(String[] noticesId) {
        BasicJson basicJson = new BasicJson(false);
        if (basicDao.delete(noticesId, Notice.class)) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("删除成功");
        }
        else {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode("06002");
            basicJson.getErrMsg().setMessage("删除失败");
        }
        return basicJson;
    }

    /**
     * 作用：修改信息
     * @param id
     * @param request
     * @return
     */
    @Override
    public BasicJson updateNotice(int id, HttpServletRequest request) {
        BasicJson basicJson = new BasicJson(false);
        Notice notice = (Notice) basicDao.get(Notice.class, id);
        notice.setTitle(request.getParameter("title"));
        notice.setContent(request.getParameter("content"));
        if (basicDao.save(notice)) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("更新成功");
        }
        else {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode("06003");
            basicJson.getErrMsg().setMessage("更新失败");
        }
        return basicJson;
    }


    /**
     * 作用：获取相应类型的信息
     * @param typeAndPageInfo
     * @return
     */
    @Override
    public BasicJson getNoticesByType(String[] typeAndPageInfo) {
        BasicJson basicJson = new BasicJson(false);
        EntityJson<Notice> noticeEntityJson = new EntityJson<Notice>();
        List<Notice> noticeList = noticeDao.getNoticesByType(typeAndPageInfo);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Notice notice : noticeList) {
            try {
                notice.setPublishDate(sdf.format(sdf.parse(notice.getPublishDate())));
            } catch (ParseException e) {
                basicJson.setStatus(false);
                basicJson.getErrMsg().setCode("06004");
                basicJson.setJsonStr("获取失败");
                return basicJson;
            }
        }
        noticeEntityJson.setEntityList(noticeList);
        Double totalPageDouble = Double.valueOf(String.valueOf(getNoticesNumByType(typeAndPageInfo[0])));
        Double requestPageNumDouble = Double.valueOf(typeAndPageInfo[2]);
        int pageNum = ((Double) Math.ceil(totalPageDouble / requestPageNumDouble)).intValue();
        noticeEntityJson.setPage(pageNum);
        basicJson.setStatus(true);
        basicJson.getErrMsg().setCode("200");
        basicJson.getErrMsg().setMessage("获取成功");
        basicJson.setJsonStr(noticeEntityJson);
        return basicJson;
    }

    /**
     * 作用：查看具体信息
     * @param id
     * @return
     */
    @Override
    public BasicJson getNotice(int id) {
        BasicJson basicJson = new BasicJson(false);
        Notice notice;
        notice = (Notice) basicDao.get(Notice.class, id);
        basicJson.setStatus(true);
        basicJson.getErrMsg().setCode("200");
        basicJson.getErrMsg().setMessage("获取成功");
        basicJson.setJsonStr(notice);
        return basicJson;
    }


    /**
     * 作用：获取相应类型的信息的个数
     * @param type
     * @return
     */
    @Override
    public int getNoticesNumByType(String type) {
        return noticeDao.getNoticesNumByType(Integer.valueOf(type));
    }



}


