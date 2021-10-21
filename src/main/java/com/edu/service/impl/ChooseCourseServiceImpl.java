package com.edu.service.impl;

import com.edu.entity.ChooseCourse;
import com.edu.mapper.ChooseCourseMapper;
import com.edu.service.ChooseCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ChooseCourseServiceImpl implements ChooseCourseService {
    @Autowired
    private ChooseCourseMapper chooseCourseMapper;

    @Override
    public int updateByPrimaryKeySelective(ChooseCourse chooseCourse) {
        return chooseCourseMapper.updateByPrimaryKeySelective(chooseCourse);
    }
}
