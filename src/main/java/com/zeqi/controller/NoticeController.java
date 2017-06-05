package com.zeqi.controller;

import com.zeqi.json.BasicJson;
import com.zeqi.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Max on 2016/11/24.
 */
@RestController
@RequestMapping("/web")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;


    /**
     * 作用：发布信息
     * @param request 请求对象
     * @returnd
     */
    @RequestMapping(value = "/notice", method=RequestMethod.POST)
    public BasicJson addNotice(HttpServletRequest request) {
        BasicJson basicJson;
        basicJson = noticeService.addNotice(request);
        return basicJson;
    }


    /**
     * 作用：删除已发布的信息
     * @param id 消息编号
     * @return
     */
    @RequestMapping(value = "/notice/{id}", method=RequestMethod.DELETE)
    public BasicJson deleteNotice(@PathVariable String id) {
        BasicJson basicJson;
        String[] noticesId = id.split("&");
        basicJson = noticeService.deleteNotice(noticesId);
        return basicJson;
    }


    /**
     * 作用：更新已发布的信息
     * @param request 请求对象
     * @return
     */
    @RequestMapping(value = "/notice/{id}", method=RequestMethod.PUT)
    public BasicJson updateNotice(@PathVariable int id, HttpServletRequest request) {
        BasicJson basicJson;
        basicJson = noticeService.updateNotice(id, request);
        return basicJson;
    }


    /**
     * 作用：查看相应类型的信息
     * type的值范围为 1,2,3,4 每个数字代表每种消息的类型，分别为： 活动信息、竞赛信息、
     */
    @RequestMapping(value = "/notices/{typeAndPage}", method= RequestMethod.GET)
    public BasicJson getNoticesByType(@PathVariable String typeAndPage) {
        BasicJson basicJson;
        String[] typeAndPageInfo = typeAndPage.split("&");
        basicJson = noticeService.getNoticesByType(typeAndPageInfo);
        return basicJson;
    }

    /**
     * 作用：查看具体信息
     * @param id: 消息编号
     * @return
     */
    @RequestMapping(value = "/notice/{id}", method=RequestMethod.GET)
    public BasicJson getOneNotice(@PathVariable int id) {
        BasicJson basicJson;
        basicJson = noticeService.getNotice(id);
        return basicJson;
    }

}
