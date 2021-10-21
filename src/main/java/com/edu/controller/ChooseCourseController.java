package com.edu.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/choosecourse")
public class ChooseCourseController {
        @RequiresRoles("学生")//角色为学生的用户才能访问
        @RequiresPermissions("course:result")//权限字符串
        @RequestMapping(value = "/showChooseResult",method = RequestMethod.GET)
        public String showChooseResult(){
            return "look-choose-result";
        }

}
