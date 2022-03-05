package com.gtmp.config;

import com.gtmp.POJO.Permission;
import com.gtmp.POJO.User;
import com.gtmp.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ShiroRealm extends AuthorizingRealm {



    @Autowired
    private UserService userService;

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        if (user.getRole() == null) {
            return info;
        }
        user = userService.selectUserWithRoleByEmail(user.getEmail());

        // add role
        info.addRole(user.getRole().getName());
        // add permission
        if (user.getRole().getPermissions() != null  && user.getRole().getPermissions().size()>0){
            List<String> list = new ArrayList<>();
            for (Permission p:user.getRole().getPermissions())
                list.add(p.getOperation());
            info.addStringPermissions(list);
        }
        return info;
    }


    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.selectUserByEmail(token.getUsername());
        System.out.println(user);

        if (user == null) {
            throw new UnknownAccountException("account not exist!");
        }

        // If info not match, throw IncorrectCredentialsException
        return new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
    }
}