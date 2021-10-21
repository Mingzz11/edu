package com.edu.controller;

import com.alibaba.fastjson.JSON;
import com.edu.entity.Term;
import com.edu.response.Result;
import com.edu.service.TermService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/term")
public class TermController {
    @Autowired
    private TermService termService;

    /**
     * 获取所有学期
     * @param notAll
     * @return
     */
    @RequestMapping(value = "/getAllTerm",method = RequestMethod.GET)
    public String getAllTerm(@Nullable Integer notAll) {
        List<Term> list = termService.getAllTerm();
        Map<String, Object> map = new HashMap<>();
        for (Term term : list) {
            if (term.getTermStatus() == 1){
                map.put("this", term.getTermId());
            }
        }
        if (notAll == null) {
            Term term = new Term();
            term.setTermId(0);
            term.setTermName("全部");
            list.add(0, term);
        }
        map.put("list", list);
        Result result = new Result();
        result.setCode(200);
        result.setData(map);
        return JSON.toJSONString(map);
    }

    /**
     * 设置当前学期
     * @param termId
     * @return
     */
    @RequiresRoles("系统管理员")//具有系统管理员角色的用户才能访问
    @RequiresPermissions("term:set")//使用字符串匹配权限。比如term:set匹配设置学期的权限。term:*可以匹配term:query,user:delete等。
    @RequestMapping(value = "/setTerm", method = RequestMethod.GET)
    public @ResponseBody String setThisTerm(Integer termId) {
        termService.setThisTerm(termId);
        Term term =termService.selectTermById(termId);
        Result result = new Result();
        result.setCode(200);
        result.setMessage("设置成功");
        result.setData(term);
        return JSON.toJSONString(result);
    }
}
