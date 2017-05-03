package com.zeqi.database;

import javax.persistence.*;

/**
 * Created by Max on 2016/10/12.
 */
@Entity
@Table(name = "student_info", schema = "", catalog = "zeqi")
public class StudentInfo {
    private String stuId;
    private String name;
    private String gender;
    private String emailAddress;
    private String nickName;
    private String grade;
    private String major;
    private String homePlace;
    private String phone;
    private String position;
    private String loveDirection;
    private String introduce;
    private String headPic;
    private String backgroundPic;

    @Id
    @Column(name = "stu_id")
    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "email_address")
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Basic
    @Column(name = "nick_name")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Basic
    @Column(name = "grade")
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Basic
    @Column(name = "major")
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Basic
    @Column(name = "home_place")
    public String getHomePlace() {
        return homePlace;
    }

    public void setHomePlace(String homePlace) {
        this.homePlace = homePlace;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "love_direction")
    public String getLoveDirection() {
        return loveDirection;
    }

    public void setLoveDirection(String loveDirection) {
        this.loveDirection = loveDirection;
    }

    @Basic
    @Column(name = "introduce")
    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Basic
    @Column(name = "head_pic")
    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    @Basic
    @Column(name = "background_pic")
    public String getBackgroundPic() {
        return backgroundPic;
    }

    public void setBackgroundPic(String backgroundPic) {
        this.backgroundPic = backgroundPic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentInfo that = (StudentInfo) o;

        if (backgroundPic != null ? !backgroundPic.equals(that.backgroundPic) : that.backgroundPic != null)
            return false;
        if (emailAddress != null ? !emailAddress.equals(that.emailAddress) : that.emailAddress != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (grade != null ? !grade.equals(that.grade) : that.grade != null) return false;
        if (headPic != null ? !headPic.equals(that.headPic) : that.headPic != null) return false;
        if (homePlace != null ? !homePlace.equals(that.homePlace) : that.homePlace != null) return false;
        if (introduce != null ? !introduce.equals(that.introduce) : that.introduce != null) return false;
        if (loveDirection != null ? !loveDirection.equals(that.loveDirection) : that.loveDirection != null)
            return false;
        if (major != null ? !major.equals(that.major) : that.major != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (nickName != null ? !nickName.equals(that.nickName) : that.nickName != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (stuId != null ? !stuId.equals(that.stuId) : that.stuId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stuId != null ? stuId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        result = 31 * result + (major != null ? major.hashCode() : 0);
        result = 31 * result + (homePlace != null ? homePlace.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (loveDirection != null ? loveDirection.hashCode() : 0);
        result = 31 * result + (introduce != null ? introduce.hashCode() : 0);
        result = 31 * result + (headPic != null ? headPic.hashCode() : 0);
        result = 31 * result + (backgroundPic != null ? backgroundPic.hashCode() : 0);
        return result;
    }
}
