package com.zeqi.database;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.sql.Timestamp;

/**
 * Created by Max on 2016/10/11.
 */
@Entity
@Table(name = "funding")
public class Funding {
    private int id;
    private String leftMoney;
    private String spentMoney;
    private String costUse;
    private String updateDate;
    private StudentInfo studentInfo;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "left_money")
    @NotNull
    public String getLeftMoney() {
        return leftMoney;
    }

    public void setLeftMoney(String leftMoney) {
        this.leftMoney = leftMoney;
    }

    @Basic
    @Column(name = "spent_money")
    @NotNull
    public String getSpentMoney() {
        return spentMoney;
    }

    public void setSpentMoney(String spentMoney) {
        this.spentMoney = spentMoney;
    }

    @Basic
    @Column(name = "cost_use")
    @NotNull
    public String getCostUse() {
        return costUse;
    }

    public void setCostUse(String costUse) {
        this.costUse = costUse;
    }

    @Basic
    @Column(name = "update_date")
    @NotNull
    public String getUpdateDate() {
        return updateDate;
    }


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "stu_id")
    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
