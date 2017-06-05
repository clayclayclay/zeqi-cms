package com.zeqi.database;

import javax.persistence.*;
import javax.validation.constraints.NotNull;



/**
 * Created by Max on 2016/10/18.
 */
@Entity
@Table(name = "work_activity_info")
public class WorkActivityInfo {
    private int id;
    private int time;
    private String title;
    private String description;
    private StudentInfo studentInfo;
    private long recodeTime;
    
    
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
    @Column(name = "time")
    @NotNull
    public int getTime() {
	
		return time;
	}

	public void setTime(int time) {
		this.time = time;
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

    @Basic
    @Column(name = "description")
    @NotNull
	public String getDescription() {
	
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
    @Basic
    @Column(name = "record_time")
    @NotNull
	public long getRecordTime() {
		return recodeTime;
	}

	public void setRecordTime(long recodeTime) {
		this.recodeTime = recodeTime;
	}

	@ManyToOne
	@JoinColumn(name = "stu_id")
	public StudentInfo getStudentInfo() {
	
		return studentInfo;
	}

	public void setStudentInfo(StudentInfo studentInfo) {
		this.studentInfo = studentInfo;
	}
}
