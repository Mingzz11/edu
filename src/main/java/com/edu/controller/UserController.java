package com.edu.controller;

import com.alibaba.fastjson.JSON;
import com.edu.entity.College;
import com.edu.entity.User;
import com.edu.response.Result;
import com.edu.service.CollegeService;
import com.edu.service.CourseService;
import com.edu.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController{
    @Autowired
    private UserService userService;
    @Autowired
    private CollegeService collegeService;
    @Autowired
    private CourseService courseService;
//    /**
//     * 用户注册
//     * @return  登录页面
//     */
//    @RequestMapping("register")
//    public String register(User user){
//        System.out.println(user.getUsername());
//        try{
//            userService.register(user);
//            return "redirect:/user/loginview";
//        }catch (Exception e){
//            e.printStackTrace();
//            return "redirect:/register.jsp";
//        }
//    }

    /**
     * 跳转到教务管理页面
     */
    @RequiresRoles("教务管理员")//角色为教务管理员的用户才能访问
    @RequestMapping("lookAllCourse")
    public String lookAllCourse(){
       return "look-all-start-course";
    }

    /**
     * 跳转到增设课程页面
     */
    @RequiresRoles("教务管理员")//角色为教务管理员的用户才能访问
    @RequestMapping("addCourse")
    public String addCourse(){
        return "add-course";
    }

    /**
     * 跳转到公共主页
     * @return
     */
    @RequiresUser//已登录的用户才能进行访问
    @RequestMapping("index")
    public String hello(){
        return "index";
    }

    /**
     * 跳转到选课主页
     *
     */
    @RequiresRoles("学生")//角色为学生的用户才能访问
    @RequestMapping("chooseCourse")
    public String chooseCourse(){
        return "choose-course";
    }

    /**
     *跳转到学生个人信息页
     */
    @RequiresRoles("学生")//角色为学生的用户才能访问
    @RequestMapping("stuIndex")
    public String stuIndex(){
        return "student-index";
    }

    /**
     * 跳转到历史选课页面
     */
    @RequiresRoles("学生")//角色为学生的用户才能访问
    @RequiresPermissions("course:history")//权限字符串
    @RequestMapping("history")
    public String history(){
        return "look-history-course";
    }


    /**
     * 获取认证通过的用户信息
     * @return
     */
    @RequiresUser//已登录的用户才能进行访问
    @RequestMapping(value = "userInfo",method = RequestMethod.POST)
    public @ResponseBody String findUserInfo(){
        Subject subject = SecurityUtils.getSubject();//获取主体对象
        Map<String, Object> map = new HashMap<>();
        Result result = new Result();
        if(subject.hasRole("学生")){
            String username = (String) subject.getPrincipal();
            User user =userService.findUserIdByUserName(username);//查询当前登录成功的用户信息
            College college =collegeService.selectByPrimaryKey(user.getCollegeId());
            Map<String,Object> map1= courseService.getChooseCourseResult(user.getUserId(),1,5);
            map.put("thisCourse",map1);
            map.put("college",college);
            if(user!=null){
                result.setCode(200);
                result.setMessage("登录成功！");
                result.setData(map);
            }
        }
        return JSON.toJSONString(result);
    }


    /**
     * 退出系统
     * @return
     */
    @RequiresAuthentication  //已登录的用户才能访问
    @RequestMapping(value = "logout")
    public @ResponseBody String logout(){
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        subject.logout();//退出登录，注销Session
        Result result = new Result();
        result.setCode(200);
        result.setMessage("退出成功！ 2秒后返回登录界面");
        return JSON.toJSONString(result);
    }

    /**
     * 访问登录页面
     * @return
     */
    @RequestMapping("loginview")
    public String loginview(){
           return "login";
    }

    /**
     *自定义登录
     * @param user
     * @return
     */
    @RequestMapping("login")
    public @ResponseBody String login(User user){
        Result result = new Result();
        if(user.getUsername()=="" || user.getPassword()==""){
              result.setCode(500);
              result.setMessage("账号密码不能为空！");
              return JSON.toJSONString(result);
        }
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(new UsernamePasswordToken(user.getUsername(),user.getPassword()));
            String username = (String) subject.getPrincipal();
            User userinfo =userService.findUserIdByUserName(username);//查询当前登录成功的用户信息
            Session session = subject.getSession();
            session.setAttribute("userinfo",userinfo);
            session.setTimeout(300000);//毫秒为单位，即在没有活动5分钟后，session将失效。
        }catch (UnknownAccountException e){
            result.setCode(401);
            result.setMessage("账号不存在！");
            return JSON.toJSONString(result);
        }catch (IncorrectCredentialsException e){
            result.setCode(402);
            result.setMessage("密码错误！");
            return JSON.toJSONString(result);
        }
        result.setCode(200);
        result.setMessage("登录成功");
        return JSON.toJSONString(result);
    }

    /**
     * 获取学院所有老师
     * @param collegeId
     * @return
     */
    @RequiresRoles("教务管理员")
    @RequestMapping(value = "/getTeachers", method = RequestMethod.GET)
    public @ResponseBody String getTeachersBySchoolId(Integer collegeId) {
        List<User> list = userService.getAllTeachersBycollegeId(collegeId);
        Result result = new Result();
        result.setData(list);
        result.setCode(200);
        return JSON.toJSONString(result);
    }
}
