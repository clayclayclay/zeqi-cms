package com.zeqi.database;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Max on 2016/10/11.
 */
@Entity
@Table(name = "funding", schema = "", catalog = "zeqi")
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
    public String getLeftMoney() {
        return leftMoney;
    }

    public void setLeftMoney(String leftMoney) {
        this.leftMoney = leftMoney;
    }

    @Basic
    @Column(name = "spent_money")
    public String getSpentMoney() {
        return spentMoney;
    }

    public void setSpentMoney(String spentMoney) {
        this.spentMoney = spentMoney;
    }

    @Basic
    @Column(name = "cost_use")
    public String getCostUse() {
        return costUse;
    }

    public void setCostUse(String costUse) {
        this.costUse = costUse;
    }

    @Basic
    @Column(name = "update_date")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Funding funding = (Funding) o;

        if (id != funding.id) return false;
        if (costUse != null ? !costUse.equals(funding.costUse) : funding.costUse != null) return false;
        if (leftMoney != null ? !leftMoney.equals(funding.leftMoney) : funding.leftMoney != null) return false;
        if (spentMoney != null ? !spentMoney.equals(funding.spentMoney) : funding.spentMoney != null) return false;
        if (updateDate != null ? !updateDate.equals(funding.updateDate) : funding.updateDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (leftMoney != null ? leftMoney.hashCode() : 0);
        result = 31 * result + (spentMoney != null ? spentMoney.hashCode() : 0);
        result = 31 * result + (costUse != null ? costUse.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }
}
