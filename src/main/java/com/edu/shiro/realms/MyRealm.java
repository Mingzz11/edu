package com.edu.shiro.realms;

import com.edu.entity.Permission;
import com.edu.entity.User;
import com.edu.mapper.UserMapper;
import com.edu.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 自定义Realm
 * 需要继承AuthorizingRealm类
 * 会实现两个方法
 * 分别为
 * doGetAuthorizationInfo该方法用于定义用户在shiro中的安全权限,也就是说我们可以在这个方法中告诉shiro当前登录的用户具有访问哪些路径或资源的权限；
 * doGetAuthenticationInfo执行认证逻辑
 */
@Component
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String principal = (String) principalCollection.getPrimaryPrincipal();//获取用户的输入的账号
        User user = userService.findRolesByUserName(principal);//系统角色
        if(!CollectionUtils.isEmpty(user.getRoles())){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            user.getRoles().forEach(role -> {
                simpleAuthorizationInfo.addRole(role.getRoleName());//给认证通过的用户添加相应的角色
                //权限信息
                List<Permission> permissions = userService.findPermissionByUserId(role.getRoleId());
                if(!CollectionUtils.isEmpty(permissions)){
                    permissions.forEach(permission -> {
                        simpleAuthorizationInfo.addStringPermission(permission.getPermissionCode());//给认证通过的用户添加相应的权限
                    });
                }
            });
            return simpleAuthorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();//获取用户的输入的账号
        User user = userService.findByUserName(principal); //通过username从数据库中查找 User对象
        if(!ObjectUtils.isEmpty(user)){//判断user对象是否为空
           //若不为空进行认证  参数：1.用户的账号 2.用户的密码 3.通过工具类SaltUtils随机生成的字符串（盐）盐–用于加密密码对比 4.当前realm的名字
            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(), ByteSource.Util.bytes(user.getSalt()),this.getName());
        }
        return null;
    }
}
