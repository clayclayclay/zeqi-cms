package com.zeqi.controller;

import com.zeqi.json.BasicJson;
import com.zeqi.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Max on 2016/11/12.
 */
//@RestController
@Controller
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
    @ResponseBody
    public BasicJson addDocument(HttpServletRequest request) {
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
    @ResponseBody
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
    public String getDocumentList(Map<String, Object> model, @PathVariable String page) {
    	Map<String, Object> map;
    	map = documentService.getDocumentList(page);
        model.put("documentList", map.get("documentDTOList"));
        model.put("documentConfig", map.get("documentConfig"));
        return "document/document_list";
    }


    /**
     * 作用：管理文档（查看，上传，删除）
     * @return
     */
    @RequestMapping(value="/documents/guy/{page}", method=RequestMethod.GET)
    public String mangeDocument(Map<String, Object> model, @PathVariable String page) {
    	Map<String, Object> map;
    	map = documentService.getDocumentList(page);
        model.put("documentList", map.get("documentDTOList"));
        model.put("documentConfig", map.get("documentConfig"));
        return "document/document_mange";
    }
    
    @RequestMapping(value="/document/{fileId}", method=RequestMethod.GET)
    @ResponseBody
    public ClassPathResource downloadDocument(@PathVariable String fileId) {
    	return new ClassPathResource("/data/document/企业实习初期检查表（企业导师版）(附件1)-1494416832291.docx");
    }
    
    
    
}
