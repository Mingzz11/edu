package com.edu.service;

import com.edu.entity.College;

public interface CollegeService {
    College selectByPrimaryKey(Integer collegeId);//查找学院信息
}
