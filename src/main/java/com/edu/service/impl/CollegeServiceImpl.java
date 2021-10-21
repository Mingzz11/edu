package com.edu.service.impl;

import com.edu.entity.College;
import com.edu.mapper.CollegeMapper;
import com.edu.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CollegeServiceImpl implements CollegeService {
    @Autowired
    private CollegeMapper collegeMapper;
    @Override
    public College selectByPrimaryKey(Integer collegeId) {
        return collegeMapper.selectByPrimaryKey(collegeId);
    }
}
