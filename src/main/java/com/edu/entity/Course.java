package com.edu.entity;

import java.io.Serializable;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer courseId; //课程id

    private String courseCode; //课程代码

    private String courseName; //课程名称

    private String courseType;//课程类型

    private Integer collegeId;//学院id

    private Integer teacherId;//教师id

    private Integer courseTermId;//开课学期id

    private String courseTerm;//开课学期名

    private Integer courseStatus;//课程状态：表示是否为本学期课程

    private String courseTime;//上课时间

    private String coursePlace;//上课地点

    private Integer courseCredit;//学分

    private Double usualWeight;//平时成绩占比

    private Double endWeight;//最终成绩占比

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getCourseTermId() {
        return courseTermId;
    }

    public void setCourseTermId(Integer courseTermId) {
        this.courseTermId = courseTermId;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(String courseTerm) {
        this.courseTerm = courseTerm;
    }

    public Integer getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(Integer courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCoursePlace() {
        return coursePlace;
    }

    public void setCoursePlace(String coursePlace) {
        this.coursePlace = coursePlace;
    }

    public Integer getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(Integer courseCredit) {
        this.courseCredit = courseCredit;
    }

    public Double getUsualWeight() {
        return usualWeight;
    }

    public void setUsualWeight(Double usualWeight) {
        this.usualWeight = usualWeight;
    }

    public Double getEndWeight() {
        return endWeight;
    }

    public void setEndWeight(Double endWeight) {
        this.endWeight = endWeight;
    }

}