package com.edu.service.impl;

import com.edu.entity.*;
import com.edu.mapper.*;
import com.edu.service.CourseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CollegeMapper collegeMapper;
    @Autowired
    private ChooseCourseMapper chooseCourseMapper;
    @Autowired
    private TermMapper termMapper;

    @Override
    public Map<String, Object> getThisCourses(Integer userId,Integer pageNum, Integer pageSize) {
        int first = pageNum == null ? 1 : pageNum; //第几页
        int second = pageSize == null ? 5 : pageSize; //每页记录数
        PageHelper.startPage(first, second);
        List<Course> courses = courseMapper.getThisCourses(userId);
        Map<String, Object> map = new HashMap<>();
        PageInfo pageInfo = new PageInfo(courses);
        map.put("total", pageInfo.getTotal());
        List<Map<String, Object>> list = new ArrayList<>();
        for (Course course : courses) {
            Map<String, Object> map1 = new HashMap<>();
            User teacher = userMapper.selectByPrimaryKey(course.getTeacherId());
            College college = collegeMapper.selectByPrimaryKey(course.getCollegeId());
            map1.put("teacher", teacher);
            map1.put("course", course);
            map1.put("college", college);
            list.add(map1);
        }
        map.put("list", list);
        return map;
    }

    @Override
    public boolean isContradict(Integer userId, Integer courseId) {
        List<Course> courses = courseMapper.getChooseCourseResult(userId);
        for (Course course : courses) {
            Course course1 = courseMapper.selectByPrimaryKey(courseId);
            if (course.getCourseTime().equals(course1.getCourseTime())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int insertChooseCourse(Integer userId, Integer courseId) {
        return chooseCourseMapper.insertChooseCourse(userId, courseId);
    }

    @Override
    public Map<String, Object> getChooseCourseResult(Integer userId, Integer pageNum, Integer pageSize) {
        int first = pageNum == null ? 1 : pageNum; //第几页
        int second = pageSize == null ? 5 : pageSize; //每页记录数
        PageHelper.startPage(first, second);
        List<Course> courses = courseMapper.getChooseCourseResult(userId);
        Map<String, Object> map = new HashMap<>();
        PageInfo pageInfo = new PageInfo(courses);
        map.put("total", pageInfo.getTotal());
        List<Map<String, Object>> list = new ArrayList<>();
        for (Course course : courses) {
            Map<String, Object> map1 = new HashMap<>();
            User teacher = userMapper.selectByPrimaryKey(course.getTeacherId());
            College college = collegeMapper.selectByPrimaryKey(course.getCollegeId());
            map1.put("teacher", teacher);
            map1.put("course", course);
            map1.put("college", college);
            list.add(map1);
        }
        map.put("list", list);
        return map;
    }

    @Override
    public int deleteChooseCourse(Integer userId, Integer courseId) {
        return chooseCourseMapper.deleteChooseCourse(userId, courseId);
    }

    @Override
    public Map<String, Object> getHistoryCourses(Integer userId, Integer term, Integer pageNum, Integer pageSize) {
        int first = pageNum == null ? 1 : pageNum; //第几页
        int second = pageSize == null ? 5 : pageSize; //每页记录数
        PageHelper.startPage(first, second);
        if (term == 0) {
            term = null;
        }
        List<Course> courses = courseMapper.getHistoryCourses(userId, term);
        PageInfo pageInfo = new PageInfo(courses);
        Map<String, Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());
        List<Map<String, Object>> list = new ArrayList<>();
        for (Course course : courses) {
            Map<String, Object> map1 = new HashMap<>();
            User teacher = userMapper.selectByPrimaryKey(course.getTeacherId());
            College college = collegeMapper.selectByPrimaryKey(course.getCollegeId());
            map1.put("teacher", teacher);
            map1.put("course", course);
            map1.put("college", college);
            list.add(map1);
        }
        map.put("list", list);
        return map;
    }

    @Override
    public Map<String, Object> getThisGrade(Integer userId, Integer pageNum, Integer pageSize) {
        int first = pageNum == null ? 1 : pageNum; //第几页
        int second = pageSize == null ? 5 : pageSize; //每页记录数
        PageHelper.startPage(first, second);
        List<Course> courses = courseMapper.getThisCoursesHasGrade(userId);
        List<Map<String, Object>> maps = new ArrayList<>();
        for (Course course : courses) {
            Map<String, Object> map1 = new HashMap<>();
            ChooseCourse chooseCourse = chooseCourseMapper.selectChooseCourseByCourseIdUserId(userId, course.getCourseId());
            User teacher = userMapper.selectByPrimaryKey(course.getTeacherId());
            map1.put("teacher",teacher.getStuName());
            map1.put("course", course);
            map1.put("grade", chooseCourse);
            maps.add(map1);
        }
        PageInfo pageInfo = new PageInfo(courses);
        Map<String, Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());
        map.put("list", maps);
        return map;
    }

    @Override
    public Map<String, Object> getHistoryGrade(Integer userId, Integer term, Integer pageNum, Integer pageSize) {
        int first = pageNum == null ? 1 : pageNum; //第几页
        int second = pageSize == null ? 5 : pageSize; //每页记录数
        PageHelper.startPage(first, second);
        if (term == 0) {
            term = null;
        }
        List<Course> courses = courseMapper.getHistoryGrade(userId, term);
        List<Map<String, Object>> maps = new ArrayList<>();
        for (Course course : courses) {
            Map<String, Object> map1 = new HashMap<>();
            ChooseCourse chooseCourse = chooseCourseMapper.selectChooseCourseByCourseIdUserId(userId, course.getCourseId());
            User teacher = userMapper.selectByPrimaryKey(course.getTeacherId());
            map1.put("teacher",teacher.getStuName());
            map1.put("course", course);
            map1.put("grade", chooseCourse);
            maps.add(map1);
        }
        PageInfo pageInfo = new PageInfo(courses);
        Map<String, Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());
        map.put("list", maps);
        return map;
    }

    @Override
    public void openChooseCourse() {
        Term term = termMapper.selectOne(1);
        List<Course> courses = courseMapper.getAllStartCourses(null, term.getTermId());
        for (Course course : courses) {
            course.setCourseStatus(1);
            courseMapper.updateByPrimaryKeySelective(course);
        }
    }

    @Override
    public void closeChooseCourse() {
        List<Course> courses =courseMapper.selectCourseList();
        for (Course course : courses) {
            course.setCourseStatus(0);
            courseMapper.updateByPrimaryKeySelective(course);
        }
    }

    @Override
    public Map<String, Object> getThisStartCourses(Integer userId, Integer pageNum, Integer pageSize) {
        int first = pageNum == null ? 1 : pageNum; //第几页
        int second = pageSize == null ? 5 : pageSize; //每页记录数
        PageHelper.startPage(first, second);
        List<Course> courses = courseMapper.getThisStartCourses(userId);
        PageInfo pageInfo = new PageInfo(courses);
        Map<String, Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());
        List<Map<String, Object>> list = new ArrayList<>();
        for (Course course : courses) {
            Map<String, Object> map1 = new HashMap<>();
            User teacher = userMapper.selectByPrimaryKey(course.getTeacherId());
            College college = collegeMapper.selectByPrimaryKey(course.getCollegeId());
            map1.put("teacher", teacher);
            map1.put("course", course);
            map1.put("college", college);
            list.add(map1);
        }
        map.put("list", list);
        return map;
    }

    @Override
    public Course getCourseById(Integer courseId) {
        return courseMapper.selectByPrimaryKey(courseId);
    }

    @Override
    public Map<String, Object> getALLStartCourses(Integer collegeId, Integer term, Integer pageNum, Integer pageSize) {
        int first = pageNum == null ? 1 : pageNum;
        int second = pageSize == null ? 5 : pageSize;
        PageHelper.startPage(first, second);
        if (term == 0) {
            term = null;
        }
        List<Course> courses = courseMapper.getAllStartCourses(collegeId, term);
        PageInfo pageInfo = new PageInfo(courses);
        Map<String, Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());
        List<Map<String, Object>> list = new ArrayList<>();
        for (Course course : courses) {
            Map<String, Object> map1 = new HashMap<>();
            User teacher = userMapper.selectByPrimaryKey(course.getTeacherId());
            College college = collegeMapper.selectByPrimaryKey(course.getCollegeId());
            map1.put("teacher", teacher);
            map1.put("course", course);
            map1.put("college", college);
            list.add(map1);
        }
        map.put("list", list);
        return map;
    }

    @Override
    public int removeById(Integer courseId) {
         return courseMapper.deleteByPrimaryKey(courseId);
    }

    @Override
    public int updateCourse(Course course) {
        return courseMapper.updateByPrimaryKeySelective(course);
    }

    @Override
    public int insertSelective(Course course) {
        return courseMapper.insertSelective(course);
    }
}
