package com.edu.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
@Aspect
public class ServiceAspect {
    private static final Logger logger = LoggerFactory.getLogger(com.edu.aspect.ServiceAspect.class);

    @Pointcut("execution(* com.edu.service.*.*(..))")
    public void pointCut(){

    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();// 接收到请求，记录请求内容
        if (attributes == null){
            return;
        }
        HttpServletRequest request =attributes.getRequest();//获取请求内容
        String ip = request.getRemoteHost();//获得客户端的主机名
        String now = new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(new Date());//时间格式化
        String target = joinPoint.getSignature().getDeclaringTypeName() + "." +joinPoint.getSignature().getName();//接口全路径+调用的方法
        logger.info(String.format("用户[%s],在[%s],访问了[%s].", ip, now, target));//使用指定的格式和字符串参数，[%s]--转换成>>>>>>字符串类型
    }
}














