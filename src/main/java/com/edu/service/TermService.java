package com.edu.service;

import com.edu.entity.Term;

import java.util.List;

public interface TermService {
    List<Term> getAllTerm();//获取所有学期信息

    void setThisTerm(Integer termId);//设置当前学期

    Term selectTermById(Integer termId);//查找学期信息
}
