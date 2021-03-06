package com.gtmp.config;


import com.gtmp.filter.ShiroAuthenticateFilter;
import com.gtmp.filter.ShiroAuthorizePermsFilter;
import com.gtmp.filter.ShiroAuthorizeRolesFilter;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    ShiroAuthenticateFilter authenticateFilter(){
        return new ShiroAuthenticateFilter();
    }

    @Bean
    ShiroAuthorizeRolesFilter authenticateRolesFilter(){
        return new ShiroAuthorizeRolesFilter();
    }

    @Bean
    ShiroAuthorizePermsFilter authenticatePermsFilter(){
        return new ShiroAuthorizePermsFilter();
    }

    @Bean
    ShiroRealm shiroRealm() {
        return new ShiroRealm();
    }

    @Bean
    public SessionDAO sessionDAO() {
        return new MemorySessionDAO();  // default dao
    }

    @Bean
    DefaultSessionManager defaultWebSessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionDAO(sessionDAO());
        return sessionManager;
    }


    @Bean
    DefaultSecurityManager securityManager() {
//        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(defaultWebSessionManager());
        securityManager.setRealm(shiroRealm());

        return securityManager;
    }


    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
        // ????????????????????????????????????login??????
//        bean.setLoginUrl("/login");
        // ?????????????????????????????????
//        bean.setSuccessUrl("/index");
        // ?????????????????????????????????????????????unauthor
//        bean.setUnauthorizedUrl("/unauthorized");

        Map<String,Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("authc", authenticateFilter());
        filterMap.put("roles", authenticateRolesFilter());
        filterMap.put("perms", authenticatePermsFilter());
        bean.setFilters(filterMap);

        Map<String, String> map = new LinkedHashMap<>();
        /**
         * Shiro ???????????????????????????????????????????????????
         * ???????????????
         * 1.anon:????????????
         * 2.authc:????????????????????????
         * 3.user???????????????rememberme??????????????????
         * 4.perms???????????????????????????
         * 5.roles???????????????????????????
         */
        map.put("/login","anon");
        map.put("/","anon");
        map.put("/index","anon");
        map.put("/profile","authc");
        map.put("/notification/**", "authc");
        map.put("/*/insert","authc");
        map.put("/normal/**","roles[normal, root]");
        map.put("/admin/**","roles[root]");
        map.put("/xd","perms[read]");
        bean.setFilterChainDefinitionMap(map);

        return bean;
    }


//    /**
//     * ??????Shiro?????????(???@RequiresRoles,@RequiresPermissions)
//     * ??????????????????bean(DefaultAdvisorAutoProxyCreator???AuthorizationAttributeSourceAdvisor)?????????????????????
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
//     * ?????? shiro ??? @RequiresPermissions??????
//     */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(new DefaultWebSecurityManager());
//        return authorizationAttributeSourceAdvisor;
//    }
//
//    /**
//     * shiro???????????????????????????????????????????????????????????????(???????????????)
//     *
//     * @return
//     */
//    @Bean
//    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
//        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
//        Properties properties = new Properties();
//        /*??????????????????*/
//        properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "/error");
//        /*??????????????????*/
//        properties.setProperty("org.apache.shiro.authz.UnauthenticatedException", "/error");
//        resolver.setExceptionMappings(properties);
//        return resolver;
//    }

}
