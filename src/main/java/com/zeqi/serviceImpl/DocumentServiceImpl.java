package com.zeqi.serviceImpl;

import com.zeqi.dao.BasicDao;
import com.zeqi.database.Article;
import com.zeqi.database.DocumentInfo;
import com.zeqi.database.StudentInfo;
import com.zeqi.dataconfig.ConstantPath;
import com.zeqi.dataconfig.DocumentConfig;
import com.zeqi.dto.ArticleIndexDTO;
import com.zeqi.dto.DocumentEntityDTO;
import com.zeqi.json.BasicJson;
import com.zeqi.json.EntityJson;
import com.zeqi.service.CommonService;
import com.zeqi.service.DocumentService;
import com.zeqi.util.AmazonS3ConnectionUtil;
import com.zeqi.util.CommonUtil;
import com.zeqi.util.DateUtil;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/11/11.
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private BasicDao basicDao;
    @Autowired
    private DocumentConfig documentConfig;
    @Autowired
    @Lazy
    private AmazonS3ConnectionUtil amazonS3ConnectionUtil;
    @Autowired
    private CommonService commonService;
    
    /**
     *上传相关文档（考核文档，比赛文档）
     * @param multipartHttpServletRequest
     * @return
     */
    @Override
    public BasicJson addDocument(MultipartHttpServletRequest multipartHttpServletRequest) {

        BasicJson basicJson = new BasicJson(false);
        Map<String,MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
        DocumentInfo  documentInfo;

        //边界检测，如果没有文件上传，则立即返回错误。
        if (fileMap == null) {
            System.out.println("没有文件上传");
            basicJson.getErrMsg().setCode("03001");
            return basicJson;
        }

        String keyName;
        if ((keyName = commonService.uploadObject(fileMap.get("document"), amazonS3ConnectionUtil.getBucketDocumentName())) != null) {
        	documentInfo = new DocumentInfo();
        	documentInfo.setDocumentName(fileMap.get("document").getOriginalFilename());
        	documentInfo.setCreateTime(DateUtil.getNowLongTime());
        	documentInfo.setStudentInfo((StudentInfo) multipartHttpServletRequest.getSession().getAttribute("student_info"));
        	String documentPath = documentConfig.getResourceConfig().get("downloadDocumentPath") + keyName;
        	documentInfo.setDocumentPath(documentPath);
        	basicDao.save(documentInfo);
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("文档上传成功");
            basicJson.setJsonStr(documentInfo.getId());
        }
        else {
            basicJson.setStatus(false);
            basicJson.getErrMsg().setCode("03001");
            basicJson.getErrMsg().setMessage("文档上传失败");
        }
        return basicJson;

        //遍历fileMap，获得MultipartFile文件对象
//        for (String key : fileMap.keySet()) {
//            MultipartFile file = fileMap.get(key);
//            String fileName = file.getOriginalFilename();
//            System.out.println(fileName);
//            try {
//                String nowTime = String.valueOf(new Date().getTime());
//                String prefix = fileName.split("\\.")[0];
//                String type = fileName.split("\\.")[1];
//                fileName = prefix + "-" + nowTime + "." + type;
//            	String uploadPath = this.getClass().getClassLoader().getResource(documentConfig.getResourceConfig().get("uploadDocumentPath")).getFile();
//                FileUtils.copyInputStreamToFile(file.getInputStream(), new File(uploadPath, fileName));
//                documentInfo = new DocumentInfo();
//                documentInfo.setDocumentName(fileName);
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                documentInfo.setCreateTime(DateConvertUtil.getNowLongTime());
//                documentInfo.setStudentInfo((StudentInfo) multipartHttpServletRequest.getSession().getAttribute("student_info"));
//                basicDao.save(documentInfo);
//                basicJson.setStatus(true);
//                basicJson.getErrMsg().setCode("200");
//                basicJson.getErrMsg().setMessage("文档上传成功");
//                basicJson.setJsonStr(documentInfo.getId());
//            } catch (IOException e) {
//                basicJson.setStatus(false);
//                basicJson.getErrMsg().setCode("03001");
//                basicJson.getErrMsg().setMessage("文档上传失败");
//            }
//        }
//        return basicJson;
    }

    /**
     * 删除文档
     * @param documentId
     * @return
     */
    @Override
    public BasicJson deleteDocument(String[] documentId) {
        BasicJson basicJson = new BasicJson(false);
        boolean isDelete;
        isDelete = basicDao.delete(documentId, DocumentInfo.class);
        if (isDelete) {
            basicJson.setStatus(true);
            basicJson.getErrMsg().setCode("200");
            basicJson.getErrMsg().setMessage("删除成功");
        }
        else {
            basicJson.getErrMsg().setCode("03002");
            basicJson.getErrMsg().setMessage("删除失败");
        }
        return basicJson;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getDocumentNum() {
        return ((List<DocumentInfo>) basicDao.getAll("DocumentInfo")).size();
    }


    /**
     * 获取文档列表
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> getDocumentList(String page) {
    	
		Map<String, Object> map = new HashMap<String, Object>();
		List<DocumentInfo> documentInfoList;
		int maxResults = Integer.valueOf(documentConfig.getPaging().get("perPageArticleNum"));
		int firstResult = (Integer.valueOf(page) - 1) * maxResults;
		documentInfoList = (List<DocumentInfo>) basicDao.paginationQuery(firstResult, maxResults, DocumentInfo.class);
		List<DocumentEntityDTO> documentEntityDTOList = new ArrayList<DocumentEntityDTO>();
		for (DocumentInfo documentInfo : documentInfoList) {
			DocumentEntityDTO documentEntityDTO = new DocumentEntityDTO();
			documentEntityDTO.setId(documentInfo.getId());
			documentEntityDTO.setDownloadUrl(documentInfo.getDocumentPath());
			documentEntityDTO.setDocumentName(documentInfo.getDocumentName());
			documentEntityDTO.setStuName(documentInfo.getStudentInfo().getName());
			documentEntityDTO.setUploadTime(DateUtil.longToString(documentInfo.getCreateTime()));
			documentEntityDTOList.add(documentEntityDTO);
		}
		Double documentNum = Double.valueOf(basicDao.getTotalCount(DocumentInfo.class));
		Double perPageDocumentNum = Double.valueOf(maxResults);
		int totalPages = ((Double) Math.ceil(documentNum / perPageDocumentNum)).intValue();
		Map<String, String> documentConfig = new HashMap<String, String>();
		documentConfig.put("currentPage", page);
		documentConfig.put("totalPages", String.valueOf(totalPages));
//		documentConfig.put("downloadDocumentPath", this.documentConfig.getResourceConfig().get("downloadDocumentPath"));
		documentConfig.put("getDocumentsUrl", this.documentConfig.getApiConfig().get("getDocumentsUrl"));
		documentConfig.put("postDocumentUrl", this.documentConfig.getApiConfig().get("postDocumentUrl"));
		documentConfig.put("manageDocumentsUrl", this.documentConfig.getApiConfig().get("manageDocumentsUrl"));
		documentConfig.put("deleteDocumentUrl", this.documentConfig.getApiConfig().get("deleteDocumentUrl"));
		map.put("documentDTOList", documentEntityDTOList);
		map.put("documentConfig", documentConfig);
		return map;
    }
}
