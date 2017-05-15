package com.zeqi.service;

import com.zeqi.database.DocumentInfo;
import com.zeqi.json.BasicJson;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/11/12.
 */
public interface DocumentService {

    BasicJson addDocument(MultipartHttpServletRequest multipartHttpServletRequest);

    BasicJson deleteDocument(String[] documentId);

    Map<String, Object> getDocumentList(String page);

    int getDocumentNum();
}
