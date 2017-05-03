package com.zeqi.database;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Max on 2016/10/16.
 */
@Entity
@Table(name = "document_info", schema = "", catalog = "zeqi")
public class DocumentInfo {
    private int id;
    private String documentName;
    private String documentPath;
    private String uploadTime;
    private String stuId;
    private String stuName;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "document_name")
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @Basic
    @Column(name = "document_path")
    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    @Basic
    @Column(name = "upload_time")
    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Basic
    @Column(name = "stu_id")
    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    @Basic
    @Column(name = "stu_name")
    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocumentInfo that = (DocumentInfo) o;

        if (id != that.id) return false;
        if (documentName != null ? !documentName.equals(that.documentName) : that.documentName != null) return false;
        if (documentPath != null ? !documentPath.equals(that.documentPath) : that.documentPath != null) return false;
        if (uploadTime != null ? !uploadTime.equals(that.uploadTime) : that.uploadTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (documentName != null ? documentName.hashCode() : 0);
        result = 31 * result + (documentPath != null ? documentPath.hashCode() : 0);
        result = 31 * result + (uploadTime != null ? uploadTime.hashCode() : 0);
        return result;
    }
}
