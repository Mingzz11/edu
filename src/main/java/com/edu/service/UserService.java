package com.edu.service;


import com.edu.entity.Permission;
import com.edu.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

//    void register(User user);//注册

    User findByUserName(String username);//根据用户名查询业务的方法

    User findRolesByUserName(String username);//根据用户信息查找用户的权限

    List<Permission> findPermissionByUserId(Integer userId);//查询权限信息

    User findUserIdByUserName(String username);//通过用户姓名查找Id

    List<User> getStudentListByCourseId(Integer courseId);//查找课程学生名单

    User getTeaderById(Integer teacherId);//通过Id查找教师用户

    List<Map<String, Object>> getStudentsGradeByCourseId(Integer courseId);//查看学生成绩

    List<User> getAllTeachersBycollegeId(Integer collegeId);//查找学院所有教师
}
