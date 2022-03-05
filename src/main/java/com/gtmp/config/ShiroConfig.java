package com.gtmp.config;


import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    @Bean
    ShiroRealm myRealm() {
        return new ShiroRealm();
    }

    @Bean
    DefaultWebSessionManager defaultWebSessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }


    @Bean
    DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm());
        securityManager.setSessionManager(defaultWebSessionManager());

        return securityManager;
    }


    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
        // 未认证状态下访问将跳转至login页面
        bean.setLoginUrl("/login");
        // 认证成功后要跳转的链接
        bean.setSuccessUrl("/index");
        // 认证但是无授限状态下访问将请求unauthor
        bean.setUnauthorizedUrl("/unauthorized");

        Map<String, String> map = new LinkedHashMap<>();
        /**
         * Shiro 的内置过滤器可以实现权限的相关拦截
         * 常用过滤器
         * 1.anon:无需认证
         * 2.authc:必须认证才能访问
         * 3.user：如果使用rememberme功能可以访问
         * 4.perms：对应权限才能访问
         * 5.role：对应角色才能访问
         */
        map.put("/login","anon");
        map.put("/","anon");
        map.put("/index","anon");
        map.put("/profile","authc");
        map.put("/*/insert","authc");
        map.put("/admin","roles[root]");
        bean.setFilterChainDefinitionMap(map);
        return bean;
    }


//    /**
//     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions)
//     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
//     *
//     * @return
//     */
//    @Bean
//    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        advisorAutoProxyCreator.setProxyTargetClass(true);
//        return advisorAutoProxyCreator;
//    }
//
//
//    /**
//     * 开启 shiro 的 @RequiresPermissions注解
//     */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(new DefaultWebSecurityManager());
//        return authorizationAttributeSourceAdvisor;
//    }
//
//    /**
//     * shiro出现权限异常可通过此异常实现制定页面的跳转(或接口跳转)
//     *
//     * @return
//     */
//    @Bean
//    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
//        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
//        Properties properties = new Properties();
//        /*未授权处理页*/
//        properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "/error");
//        /*身份没有认证*/
//        properties.setProperty("org.apache.shiro.authz.UnauthenticatedException", "/error");
//        resolver.setExceptionMappings(properties);
//        return resolver;
//    }

}
