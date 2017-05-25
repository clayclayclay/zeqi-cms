package com.zeqi.database;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Max on 2016/10/12.
 */
@Entity
@Table(name = "student_account")
public class StudentAccount {
    private String stuId;
    private String password;
    private StudentInfo studentInfo;

    @Id
    @Column(name = "stu_id")
    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "stu_id")
    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentAccount that = (StudentAccount) o;

        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (stuId != null ? !stuId.equals(that.stuId) : that.stuId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stuId != null ? stuId.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
