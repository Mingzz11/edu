package com.edu.controller;

import com.alibaba.fastjson.JSON;
import com.edu.entity.ChooseCourse;
import com.edu.entity.Course;
import com.edu.entity.User;
import com.edu.response.Result;
import com.edu.service.ChooseCourseService;
import com.edu.service.CourseService;
import com.edu.service.UserService;
import com.edu.utils.SaltUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChooseCourseService chooseCourseService;
    /**
     * 跳转到本学期成绩
     *
     */
    @RequiresRoles("学生")//角色为学生的用户才能访问
    @RequiresPermissions("grade:this")//权限字符串
    @RequestMapping("thisGrade")
    public String thisGrade(){
        return "look-this-grade";
    }

    @RequiresRoles("学生")//角色为学生的用户才能访问
    @RequiresPermissions("grade:this")//权限字符串
    @RequestMapping(value = "/showThis", method = RequestMethod.GET)
    public @ResponseBody String showThisGrade(Integer userId, @Nullable String pageNum, @Nullable String pageSize) {
        Integer first = SaltUtils.changeString(pageNum);
        Integer second = SaltUtils.changeString(pageSize);
        Map<String, Object> map = courseService.getThisGrade(userId, first, second);
        Result result = new Result();
        result.setCode(200);
        result.setData(map);
        return JSON.toJSONString(result);
    }

    @RequiresRoles("学生")//角色为学生的用户才能访问
    @RequiresPermissions("grade:history")//权限字符串
    @RequestMapping("historyGrade")
    public String historyGrade(){
        return "look-history-grade";
    }

    @RequiresRoles("学生")//角色为学生的用户才能访问
    @RequiresPermissions("grade:history")//权限字符串
    @RequestMapping(value = "/showHistory", method = RequestMethod.GET)
    public @ResponseBody String showHistoryGrade(Integer userId, Integer term, @Nullable String pageNum, @Nullable String pageSize) {
        Integer first = SaltUtils.changeString(pageNum);
        Integer second = SaltUtils.changeString(pageSize);
        Map<String, Object> map = courseService.getHistoryGrade(userId, term, first, second);
        Result result = new Result();
        result.setCode(200);
        result.setData(map);
        return JSON.toJSONString(result);
    }

    /**
     * 查看学生成绩
     * @param courseId
     * @return
     */
    @RequiresRoles("老师")//角色为老师的用户才能访问
    @RequiresPermissions("grade_manage:in")
    @RequestMapping(value = "/showStudentsCourseGrade", method = RequestMethod.GET)
    public @ResponseBody String showStudentsCourseGrade(Integer courseId) {
        List<Map<String, Object>> maps = userService.getStudentsGradeByCourseId(courseId);
        Map<String, Object> map = new HashMap<>();
        map.put("list", maps);
        Result result = new Result();
        result.setCode(200);
        result.setData(map);
        return JSON.toJSONString(result);
    }

    /**
     * 录入成绩
     * @param chooseCourse
     * @return
     */
    @RequiresRoles("老师")//角色为老师的用户才能访问
    @RequiresPermissions("grade_manage:in")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String updateGrade(@RequestBody ChooseCourse chooseCourse) {
        if (chooseCourse.getEndPerformance() != null && chooseCourse.getUsualPerformance() != null) {
            chooseCourse.setStatus(1);
        }
        if(chooseCourse.getEndPerformance()>=60){
            chooseCourse.setIsPass("是");
        }
        chooseCourseService.updateByPrimaryKeySelective(chooseCourse);
        Result result = new Result();
        result.setCode(200);
        result.setMessage("录入成功");
        return JSON.toJSONString(result);
    }

}
