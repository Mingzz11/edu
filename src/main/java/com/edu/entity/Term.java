package com.edu.entity;


import java.io.Serializable;

public class Term implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer termId;

    private String termName;

    private Integer termStatus;

    public Integer getTermId() {
        return termId;
    }

    public void setTermId(Integer termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Integer getTermStatus() {
        return termStatus;
    }

    public void setTermStatus(Integer termStatus) {
        this.termStatus = termStatus;
    }
}
