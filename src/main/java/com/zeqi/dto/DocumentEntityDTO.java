package com.zeqi.dto;

import java.util.Map;

/**
 * Created by Max on 2016/10/16.
 */
public class DocumentEntityDTO {
    private int id;
    private String documentName;
    private String uploadTime;
    private String stuName;
    private String downloadUrl;
    private Map<String, String> apiConfig;
    
	public int getId() {
	
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDocumentName() {
	
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getUploadTime() {
	
		return uploadTime;
	}
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getStuName() {
	
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getDownloadUrl() {
	
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public Map<String, String> getApiConfig() {
	
		return apiConfig;
	}
	public void setApiConfig(Map<String, String> apiConfig) {
		this.apiConfig = apiConfig;
	}
	
	


	

 
}
