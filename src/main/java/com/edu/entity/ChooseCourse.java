package com.edu.entity;

import java.io.Serializable;

public class ChooseCourse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer userId;

    private Integer courseId;

    private Double usualPerformance;

    private Double endPerformance;

    private Double totalPerformance;

    private Integer status;

    private String isPass;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Double getUsualPerformance() {
        return usualPerformance;
    }

    public void setUsualPerformance(Double usualPerformance) {
        this.usualPerformance = usualPerformance;
    }

    public Double getEndPerformance() {
        return endPerformance;
    }

    public void setEndPerformance(Double endPerformance) {
        this.endPerformance = endPerformance;
    }

    public Double getTotalPerformance() {
        return totalPerformance;
    }

    public void setTotalPerformance(Double totalPerformance) {
        this.totalPerformance = totalPerformance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

}