package com.zeqi.database;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.sql.Timestamp;

/**
 * Created by Max on 2016/10/11.
 */
@Entity
@Table(name = "article", schema = "", catalog = "zeqi")
public class Article {

    private int id;
    private String title;
    private String content;
    private int readCount;
    private int commentCount;
    private long createTime;
    private StudentInfo studentInfo;

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
    @Column(name = "read_count")
    public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
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

	
	@Column(name = "content", columnDefinition="TEXT")
	@NotNull
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "comment_count")
    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    @Basic
    @Column(name = "title")
    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="stuId")
    public StudentInfo getStudentInfo() {
		return studentInfo;
	}

	public void setStudentInfo(StudentInfo studentInfo) {
		this.studentInfo = studentInfo;
	}

}
