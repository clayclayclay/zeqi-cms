package com.zeqi.controller;

import com.zeqi.database.DocumentInfo;
import com.zeqi.json.BasicJson;
import com.zeqi.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Max on 2016/11/12.
 */
@RestController
@RequestMapping("/web")
public class DocumentController {


    @Autowired
    private DocumentService documentService;


    /**
     * 作用：上传文档
     * @param request
     * @return
     */
    @RequestMapping(value="/document", method= RequestMethod.POST)
    public BasicJson addDocument(HttpServletRequest request) {
        System.out.println(request.getSession().getAttribute("student_info"));
        System.out.println("收到文档上传请求：addDocument() is called");
        BasicJson basicjson;
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            basicjson = documentService.addDocument(multipartHttpServletRequest);
            return basicjson;
        }
        else {
            return null;
        }
    }

    /**
     * 作用：删除文档
     * @return
     */
    @RequestMapping(value="/document/{id}", method=RequestMethod.DELETE)
    public BasicJson deleteDocument(@PathVariable String id, HttpServletRequest request) {
        String[] documentId = id.split("&");
        BasicJson basicJson;
        basicJson = documentService.deleteDocument(documentId);
        return basicJson;
    }

    /**
     * 作用：查看文档列表
     * @return
     */
    @RequestMapping(value="/documents/{page}", method=RequestMethod.GET)
    public BasicJson getDocumentList(@PathVariable String page) {
        BasicJson basicJson;
        String[] pageInfo = page.split("&");
        basicJson = documentService.getDocumentList(pageInfo);
        return basicJson;
    }
}
