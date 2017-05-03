package com.zeqi.serviceImpl;

import com.zeqi.constant.ConstantPath;
import com.zeqi.dao.BasicDao;
import com.zeqi.database.Article;
import com.zeqi.database.DocumentInfo;
import com.zeqi.database.StudentInfo;
import com.zeqi.json.BasicJson;
import com.zeqi.json.EntityJson;
import com.zeqi.service.DocumentService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/11/11.
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private BasicDao basicDao;



    /**
     *上传相关文档（考核文档，比赛文档）
     * @param multipartHttpServletRequest
     * @return
     */
    @Override
    public BasicJson addDocument(MultipartHttpServletRequest multipartHttpServletRequest) {

        BasicJson basicJson = new BasicJson(false);
        System.out.println("进入到service.addDocument() is called");
        Map<String,MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
        DocumentInfo  documentInfo;

        //边界检测，如果没有文件上传，则立即返回错误。
        if (fileMap == null) {
            System.out.println("没有文件上传");
            basicJson.getErrMsg().setCode("03001");
            return basicJson;
        }

        //确定文件上传路径
        String documentPath = ConstantPath.DOCUMENT_PHYSICAL_PATH;

        //遍历fileMap，获得MultipartFile文件对象
        for (String key : fileMap.keySet()) {
            MultipartFile file = fileMap.get(key);
            String fileName = file.getOriginalFilename();
            System.out.println(fileName);
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), new File(documentPath, fileName));
                documentInfo = new DocumentInfo();
                documentInfo.setDocumentName(fileName);
                documentInfo.setDocumentPath(ConstantPath.DOCUMENT_URL +  fileName);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = sdf.format(new Date());
                documentInfo.setUploadTime(strDate);
                documentInfo.setStuId(((StudentInfo) multipartHttpServletRequest.getSession().getAttribute("student_info")).getStuId());
                documentInfo.setStuName(((StudentInfo) multipartHttpServletRequest.getSession().getAttribute("student_info")).getName());
                basicDao.save(documentInfo);
                basicJson.setStatus(true);
                basicJson.getErrMsg().setCode("200");
                basicJson.getErrMsg().setMessage("文档上传成功");
                basicJson.setJsonStr(documentInfo.getId());
            } catch (IOException e) {
                basicJson.setStatus(false);
                basicJson.getErrMsg().setCode("03001");
                basicJson.getErrMsg().setMessage("文档上传失败");
            }
        }
        return basicJson;
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
        isDelete = basicDao.delete(documentId, "DocumentInfo");
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
    public BasicJson getDocumentList(String[] pageInfo) {
        BasicJson basicJson = new BasicJson(false);
        List<DocumentInfo> documentInfoList = (List<DocumentInfo>) basicDao.getAllByPage("DocumentInfo", pageInfo, true, "uploadTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (DocumentInfo documentInfo : documentInfoList) {
            try {
                documentInfo.setUploadTime(sdf.format(sdf.parse(documentInfo.getUploadTime())));
            } catch (ParseException e) {
                basicJson.setStatus(false);
                basicJson.getErrMsg().setCode("03003");
                basicJson.setJsonStr("获取失败");
                return basicJson;
            }
        }
        basicJson.setStatus(true);
        basicJson.getErrMsg().setCode("200");
        basicJson.getErrMsg().setMessage("获取成功");
        EntityJson<DocumentInfo> documentInfoEntityJson = new EntityJson<DocumentInfo>();
        documentInfoEntityJson.setEntityList(documentInfoList);
        Double totalPageDouble = Double.valueOf(String.valueOf(getDocumentNum()));
        Double requestPageNumDouble = Double.valueOf(pageInfo[1]);
        int pageNum = ((Double)Math.ceil(totalPageDouble / requestPageNumDouble)).intValue();
        documentInfoEntityJson.setPage(pageNum);
        basicJson.setJsonStr(documentInfoEntityJson);
        return basicJson;
    }
}
