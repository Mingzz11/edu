package com.edu.controller;

import com.alibaba.fastjson.JSON;
import com.edu.entity.*;
import com.edu.mapper.TermMapper;
import com.edu.response.Result;
import com.edu.service.CollegeService;
import com.edu.service.CourseService;
import com.edu.service.UserService;
import com.edu.utils.SaltUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CollegeService collegeService;
    @Autowired
    private TermMapper termMapper;
    /**
     * 获取可选课程列表
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequiresRoles("学生")//角色为学生的用户才能访问
    @RequiresPermissions("course:choose")//权限字符串
    @RequestMapping(value = "/showThis",method = RequestMethod.POST)
    public String getThisCourses(String userId, @Nullable String pageNum, @Nullable String pageSize){
        Integer first = SaltUtils.changeString(pageNum);
        Integer second = SaltUtils.changeString(pageSize);
        Integer id = Integer.valueOf(userId);
        Result result = new Result();
        Map<String, Object> map = courseService.getThisCourses(id,first,second);
        if ((long)(map.get("total")) == 0){
            result.setCode(200);
            result.setMessage("当前无可选课程，或者不在选课时间");
            return JSON.toJSONString(result);
        }
        result.setCode(200);
        result.setData(map);
        return JSON.toJSONString(result);
    }

    /**
     *查看选课结果
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequiresRoles("学生")//角色为学生的用户才能访问
    @RequiresPermissions("course:result")//权限字符串
    @RequestMapping(value = "/showResult", method = RequestMethod.POST)
    public  String getChooseCourseResult( String userId, @Nullable String pageNum, @Nullable String pageSize){
        Integer first = SaltUtils.changeString(pageNum);
        Integer second = SaltUtils.changeString(pageSize);
        Integer id = Integer.valueOf(userId);
        Map<String, Object> map = courseService.getChooseCourseResult(id, first, second);
        Result result = new Result();
        result.setCode(200);
        result.setData(map);
        return JSON.toJSONString(result);
    }

    /**
     * 选课
     * @param chooseCourse
     * @return
     */
    @RequiresRoles("学生")//角色为学生的用户才能访问
    @RequiresPermissions("course:choose")//权限字符串
    @RequestMapping(value = "/choose",method = RequestMethod.POST)
    public String chooseCourse(@RequestBody ChooseCourse chooseCourse){
        if (courseService.isContradict(chooseCourse.getUserId(),chooseCourse.getCourseId() )){//判断课程时间是否冲突，若冲突返回false
            Result result = new Result();
            result.setCode(500);
            result.setMessage("选课冲突,该时间段存在已选课程");
            return JSON.toJSONString(result);
        }
        int cresult = courseService.insertChooseCourse(chooseCourse.getUserId(), chooseCourse.getCourseId());
        if (cresult == 1){
            Result result = new Result();
            result.setCode(200);
            result.setMessage("选课成功");
            return JSON.toJSONString(result);
        }else {
            Result result = new Result();
            result.setCode(500);
            result.setMessage("服务器异常,选课失败");
            return JSON.toJSONString(result);
        }
    }

    /**
     * 退课
     * @param chooseCourse
     * @return
     */
    @RequiresRoles("学生")//角色为学生的用户才能访问
    @RequiresPermissions("course:drop")//权限字符串
    @RequestMapping(value = "/drop", method = RequestMethod.POST)
    public String deleteChooseCourse(@RequestBody ChooseCourse chooseCourse){
        int cresult = courseService.deleteChooseCourse(chooseCourse.getUserId(), chooseCourse.getCourseId());
        if (cresult == 1){
            Result result = new Result();
            result.setCode(200);
            result.setMessage("退课成功");
            return JSON.toJSONString(result);
        }else {
            Result result = new Result();
            result.setCode(500);
            result.setMessage("服务器异常,退课失败");
            return JSON.toJSONString(result);
        }
    }

    /**
     * 查看历史选课
     * @param userId
     * @param term
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequiresRoles("学生")//角色为学生的用户才能访问
    @RequiresPermissions("course:history")//权限字符串
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public String getHistoryCourses(Integer userId, Integer term, @Nullable String pageNum, @Nullable String pageSize){
        Integer first = SaltUtils.changeString(pageNum);
        Integer second = SaltUtils.changeString(pageSize);
        Map<String, Object> map = courseService.getHistoryCourses(userId, term, first, second);
        Result result = new Result();
        result.setCode(200);
        result.setData(map);
        return JSON.toJSONString(result);
    }


    /**
     * 开启选课
     * @return
     */
    @RequiresRoles("系统管理员")//角色为系统管理员的用户才能访问
    @RequiresPermissions("choose_manage:open")//权限字符串
    @RequestMapping(value = "/open",method = RequestMethod.POST)
    public @ResponseBody String openChooseCourse(){
        courseService.openChooseCourse();
        Result result = new Result();
        result.setCode(200);
        result.setMessage("开启成功");
        return JSON.toJSONString(result);
    }

    /**
     * 关闭选课
     * @return
     */
    @RequiresRoles("系统管理员")//角色为系统管理员的用户才能访问
    @RequiresPermissions("choose_manage:close")
    @RequestMapping(value = "/close",method = RequestMethod.POST)
    public @ResponseBody String closeChooseCourse(){
        courseService.closeChooseCourse();
        Result result = new Result();
        result.setCode(200);
        result.setMessage("关闭成功");
        return JSON.toJSONString(result);
    }

    /**
     *查看本学期开课
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequiresRoles("老师")//角色为老师的用户才能访问
    @RequiresPermissions("course_start:this")
    @RequestMapping(value = "/showThisStart", method = RequestMethod.GET)
    public String getThisStartCourses(Integer userId, @Nullable String pageNum, @Nullable String pageSize){
        Integer first = SaltUtils.changeString(pageNum);
        Integer second = SaltUtils.changeString(pageSize);
        Map<String, Object> map = courseService.getThisStartCourses(userId, first, second);
        Result result = new Result();
        result.setCode(200);
        result.setData(map);
        return JSON.toJSONString(result);
    }

    /**
     * 查看课程学生名单
     * @param courseId
     * @return
     */
    @RequiresRoles("老师")//角色为老师的用户才能访问
    @RequiresPermissions("showCourseStudent")
    @RequestMapping(value = "/showStudentList", method = RequestMethod.GET)
    public @ResponseBody String showStudentList(Integer courseId){
        List<User> users = userService.getStudentListByCourseId(courseId);
        Map<String, Object>  map = new HashMap<>();
        College college = collegeService.selectByPrimaryKey(courseId);
        map.put("college",college);
        map.put("list", users);
        Result result = new Result();
        if(CollectionUtils.isNotEmpty(users)){
            result.setCode(200);
            result.setData(map);
        }else{
            result.setMessage("暂无学生信息！");
            result.setCode(404);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 查看学院所有课程
     * @param collegeId
     * @param term
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequiresRoles("教务管理员")//角色为教务管理员的用户才能访问
    @RequiresPermissions("course_manage:look")
    @RequestMapping(value = "/showAllStart", method = RequestMethod.GET)
    public @ResponseBody String getAllStartCourses(Integer collegeId, Integer term, @Nullable String pageNum, @Nullable String pageSize){
        Integer first = SaltUtils.changeString(pageNum);
        Integer second = SaltUtils.changeString(pageSize);
        Map<String, Object> map = courseService.getALLStartCourses(collegeId, term, first, second);
        Result result = new Result();
        result.setCode(200);
        result.setData(map);
        return JSON.toJSONString(result);
    }

    /**
     * 删除课程
     * @param courseId
     * @return
     */
    @RequiresRoles("教务管理员")//角色为教务管理员的用户才能访问
    @RequiresPermissions("course_manage:delete")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public @ResponseBody String deleteCourse(Integer courseId){
        int i = courseService.removeById(courseId);
        Result result = new Result();
        if(i>0){
            result.setCode(200);
            result.setMessage("删除成功");
        }else{
            result.setCode(500);
            result.setMessage("删除失败");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 修改课程
     * @param course
     * @return
     */
    @RequiresRoles("教务管理员")//角色为教务管理员的用户才能访问
    @RequiresPermissions("course_manage:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String updateCourse(@RequestBody  Course course){
        int i =courseService.updateCourse(course);
        Result result = new Result();
        if(i>0){
            result.setCode(200);
            result.setMessage("修改成功");
        }else{
            result.setCode(500);
            result.setMessage("修改失败");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 增加课程
     * @param course
     * @return
     */
    @RequiresRoles("教务管理员")//角色为教务管理员的用户才能访问
    @RequiresPermissions("course_manage:add")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public @ResponseBody String addCourse(@RequestBody Course course){
        Term term = termMapper.selectTermById(course.getCourseTermId());
        course.setCourseTerm(term.getTermName());
        course.setCollegeId(1);
        int i =courseService.insertSelective(course);
        Result result = new Result();
        if(i>0){
            result.setCode(200);
            result.setMessage("添加成功");
        }else{
            result.setCode(500);
            result.setMessage("添加失败");
        }
        return JSON.toJSONString(result);
    }
}
