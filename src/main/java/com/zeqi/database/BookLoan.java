package com.zeqi.database;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Max on 2016/10/11.
 */
@Entity
@Table(name = "book_loan")
public class BookLoan {
    private int id;
    private String borrowTime;
    private String returnTime;
    private String note;
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
    @Column(name = "borrow_time")
    @NotNull
    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    @Basic
    @Column(name = "return_time")
    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    @OneToOne
    @JoinColumn(name = "stu_id")
	public StudentInfo getStudentInfo() {
	
		return studentInfo;
	}

	public void setStudentInfo(StudentInfo studentInfo) {
		this.studentInfo = studentInfo;
	}

    

}
