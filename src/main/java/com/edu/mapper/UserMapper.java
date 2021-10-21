package com.edu.mapper;

import com.edu.entity.Permission;
import com.edu.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface UserMapper {

//    void save(User user);//注册
    User findByUserName(String username);//查找用户

    User findRolesByUserName(String username);

    List<Permission> findPermissionByUserId(Integer userId);//查找用户的权限资源

    User selectByPrimaryKey(Integer userId);//查找用户

    User selectUserId(String username);//查找用户

//    int updateUser(User user);//修改密码

    List<User> getStudentListByCourseId(Integer courseId);//根据课程id查找课程的学生名单

    List<User> getAllTeachersByCollegeId(Integer collegeId);//根据学院id查找学院的所有教师
}