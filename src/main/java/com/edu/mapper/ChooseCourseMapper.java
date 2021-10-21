package com.edu.mapper;

import com.edu.entity.ChooseCourse;
import org.apache.ibatis.annotations.Param;

public interface ChooseCourseMapper {
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("courseId") Integer courseId);

    int insert(ChooseCourse record);

    int insertSelective(ChooseCourse record);

    int insertChooseCourse(int userId, int courseId);

    ChooseCourse selectByPrimaryKey(@Param("userId") Integer userId, @Param("courseId") Integer courseId);

    int updateByPrimaryKeySelective(ChooseCourse chooseCourse);

    int updateByPrimaryKey(ChooseCourse record);

    int deleteChooseCourse(Integer userId, Integer courseId);

    ChooseCourse selectChooseCourseByCourseIdUserId(Integer userId, Integer courseId); // 根据用户id+课程id 查找课程的学生名单
}