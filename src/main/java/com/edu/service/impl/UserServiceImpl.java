package com.edu.service.impl;

import com.edu.entity.ChooseCourse;
import com.edu.entity.Permission;
import com.edu.entity.User;
import com.edu.mapper.ChooseCourseMapper;
import com.edu.mapper.UserMapper;
import com.edu.service.UserService;
import com.edu.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ChooseCourseMapper chooseCourseMapper;
//    @Override
//    public void register(User user) {
//        //生成随机盐
//        String salt = SaltUtils.getSalt(8);
//        //保存随机盐
//        user.setSalt(salt);
//        //明文密码进行dm5 + salt(随机盐) + hash散列
//        Md5Hash md5Hash = new Md5Hash(user.getPassword(),salt,1024);
//        user.setPassword(md5Hash.toHex());
//        userMapper.save(user);
//    }

//    @Override
//    public int updateUser(User user) {
//        String salt = SaltUtils.getSalt(8);
//        //保存随机盐
//        user.setSalt(salt);
//        //明文密码进行dm5 + salt(随机盐) + hash散列
//        Md5Hash md5Hash = new Md5Hash(user.getPassword(),salt,1024);
//        user.setPassword(md5Hash.toHex());
//        return userMapper.updateUser(user);
//    }

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public User findRolesByUserName(String username) {
        return userMapper.findRolesByUserName(username);
    }

    @Override
    public List<Permission> findPermissionByUserId(Integer userId) {
        return userMapper.findPermissionByUserId(userId);
    }

    @Override
    public User findUserIdByUserName(String username) {
        return userMapper.selectUserId(username);
    }

    @Override
    public List<User> getStudentListByCourseId(Integer courseId) {
        return userMapper.getStudentListByCourseId(courseId);
    }

    @Override
    public User getTeaderById(Integer teacherId) {
        return userMapper.selectByPrimaryKey(teacherId);
    }

    @Override
    public List<Map<String, Object>> getStudentsGradeByCourseId(Integer courseId) {
        List<Map<String, Object>> maps = new ArrayList<>();
        List<User> users = userMapper.getStudentListByCourseId(courseId);
        for(User user:users){
            ChooseCourse chooseCourse = chooseCourseMapper.selectChooseCourseByCourseIdUserId(user.getUserId(),courseId);
            Map<String, Object> map = new HashMap<>();
            map.put("student", user);
            map.put("grade", chooseCourse);
            maps.add(map);
        }
        return maps;
    }

    @Override
    public List<User> getAllTeachersBycollegeId(Integer collegeId) {
        return userMapper.getAllTeachersByCollegeId(collegeId);
    }
}
