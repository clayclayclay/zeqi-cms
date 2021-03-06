package com.zeqi.database;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.sql.Timestamp;

/**
 * Created by Max on 2016/10/16.
 */
@Entity
@Table(name = "document_info")
public class DocumentInfo {
    private int id;
    private String documentName;
    private long createTime;
    private StudentInfo studentInfo;
    private String documentPath;

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
    @NotNull
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

   

    @Basic
    @Column(name = "create_time")
    @NotNull
    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
    
    
    @Basic
    @Column(name = "document_path")
    @NotNull
    public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	@ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="stuId")
    public StudentInfo getStudentInfo() {
		return studentInfo;
	}

	public void setStudentInfo(StudentInfo studentInfo) {
		this.studentInfo = studentInfo;
	}

}
