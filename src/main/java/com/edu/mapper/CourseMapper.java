package com.edu.mapper;

import com.edu.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapper {
    int deleteByPrimaryKey(Integer courseId);//删除课程

    List<Course> getThisCourses(Integer userId);//根据用户id查找可选课程

    List<Course> getChooseCourseResult(Integer userId);//根据用户id查找选课结果

    int insertSelective(Course record);//新增课程

    Course selectByPrimaryKey(Integer courseId);//根据课程id查找课程信息

    int updateByPrimaryKeySelective(Course courseId);//更新课程信息

    List<Course> selectCourseList();//获取所有课程信息

    List<Course> getHistoryCourses(Integer userId, Integer term);//根据用户id+学期id查找历史课程

    List<Course> getThisCoursesHasGrade(Integer userId);//根据用户id查找本学期课程

    List<Course> getHistoryGrade(Integer userId, Integer term);//根据用户id+学期id查找历史课程成绩

    List<Course> getAllStartCourses(Integer collegeId,Integer termId);///根据学院id+学期id查找学院所有课程

    List<Course> getThisStartCourses(Integer userId);//根据用户id查找本学期开设课程
}