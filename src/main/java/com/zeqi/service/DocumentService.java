package com.zeqi.service;

import com.zeqi.database.DocumentInfo;
import com.zeqi.json.BasicJson;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * Created by Max on 2016/11/12.
 */
public interface DocumentService {

    BasicJson addDocument(MultipartHttpServletRequest multipartHttpServletRequest);

    BasicJson deleteDocument(String[] documentId);

    BasicJson getDocumentList(String[] pageInfo);

    int getDocumentNum();
}
