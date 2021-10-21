package com.edu.exception;

import com.alibaba.fastjson.JSON;
import com.edu.response.Result;
import com.edu.utils.WebUtils;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalException {
    private static final Logger log = LoggerFactory.getLogger(com.edu.exception.GlobalException.class);
    @ExceptionHandler(value = UnauthorizedException.class)
    public ModelAndView  handler(UnauthorizedException e) {//权限不足
        log.error("运行时异常：----------------{}", e.getMessage());
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }
    @ExceptionHandler(value = UnauthenticatedException.class)
    public ModelAndView handler(UnauthenticatedException e) {//未登录
        log.error("运行时异常：----------------{}", e.getMessage());
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

//    @ExceptionHandler(value = UnauthenticatedException.class)
//    public ModelAndView handler(UnauthenticatedException e) {//未登录
//        log.error("运行时异常：----------------{}", e.getMessage());
//        ModelAndView modelAndView = new ModelAndView("/user/loginview");
//        return modelAndView;
//    }
}
