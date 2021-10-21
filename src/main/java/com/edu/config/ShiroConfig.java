package com.edu.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.edu.shiro.realms.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * 整合shiro框架相关的配置类
 */
@Configuration
public class ShiroConfig {
    /**
     * 注册这个bean目的就是为了在thymeleaf中使用shiro的自定义标签。
     * @return at.pollux.thymeleaf.shiro.dialect.ShiroDialect
     */
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
    //创建shiroFilter
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //添加Shiro内置过滤器
        //Shiro内置过滤器，可以实现权限相关的拦截器 规则：anon无需认证就能访问 authc：必须认证才能访问
        Map<String, String> map = new HashMap<String, String>();
        //配置系统公共资源
        map.put("/login.html","anon");//登录页面
        map.put("/user/login","anon");//用户登录的方法
        map.put("/user/logout","anon");//用户退出的方法
        map.put("/img/**","anon");//静态资源img(图片)
        map.put("/js/**","anon");//静态资源js(插件)
        map.put("/css/**","anon");//静态资源css
        map.put("/fonts/**","anon");//静态资源fonts
        map.put("/error.html","anon");//错误信息页面
        //配置系统受限资源
        map.put("/**","authc");//请求这个资源需要认证和授权
        //这里定义用户未认证时跳转的路径
        shiroFilterFactoryBean.setLoginUrl("/user/loginview");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
    //创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //给安全管理器设置realm
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }


    //创建自定义realm
    @Bean("realm")
    public Realm getRealm(){
        MyRealm myRealm = new MyRealm();
        //修改凭证校验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置加密算法为md5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);
        myRealm.setCredentialsMatcher(credentialsMatcher);

        //开启缓存管理
        myRealm.setCacheManager(new EhCacheManager());
        myRealm.setCachingEnabled(true);//开启全局缓存
        myRealm.setAuthorizationCachingEnabled(true);//开启认证缓存
        myRealm.setAuthenticationCacheName("authenticationCache");
        myRealm.setAuthenticationCachingEnabled(true);//开启授权缓存
        myRealm.setAuthorizationCacheName("authorizationCache");
        return myRealm;
    }
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        /**
         * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
         * 在@Controller注解的类的方法中加入@RequiresRole等shiro注解，会导致该方法无法映射请求，导致返回404。 加入这项配置能解决这个bug
         */
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }
}
