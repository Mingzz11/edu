package com.edu.service;

import com.edu.entity.Course;

import java.util.Map;

public interface CourseService {
    Map<String, Object> getThisCourses(Integer userId,Integer pageNum, Integer pageSize);//获取可选课程

    boolean isContradict(Integer userId, Integer courseId);//判断选课是否有时间冲突

    int insertChooseCourse(Integer userId, Integer courseId);//选课

    Map<String, Object> getChooseCourseResult(Integer userId, Integer pageNum, Integer pageSize);//查找选课结果

    int deleteChooseCourse(Integer userId, Integer courseId);//退课

    Map<String, Object> getHistoryCourses(Integer userId, Integer term, Integer pageNum, Integer pageSize);//查找历史课程

    Map<String, Object> getThisGrade(Integer userId, Integer pageNum, Integer pageSize);//查找本学期课程成绩

    Map<String, Object> getHistoryGrade(Integer userId, Integer term, Integer pageNum, Integer pageSize);//查找历史课程成绩

    void openChooseCourse();//开放选课

    void closeChooseCourse();//关闭选课

    Map<String, Object> getThisStartCourses(Integer userId, Integer pageNum, Integer pageSize);//查找本学期开设课程

    Course getCourseById(Integer courseId);//查找课程信息

    Map<String, Object> getALLStartCourses(Integer collegeId, Integer term, Integer pageNum, Integer pageSize);//查询学院所有课程

    int removeById(Integer courseId);//删除课程信息

    int updateCourse(Course course);//更新课程信息

    int insertSelective(Course course);//新增课程
}
