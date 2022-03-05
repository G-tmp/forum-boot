//package com.gtmp.interceptor;
//
//import com.gtmp.annotation.LoginRequired;
//import com.gtmp.util.ForumUtil;
//import com.gtmp.util.HostHolder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.PrintWriter;
//import java.lang.reflect.Method;
//
//
///**
// *  implement LoginRequired annotation
// */
//@Component
//public class LoginRequiredInterceptor implements HandlerInterceptor {
//    private static final Logger logger = LoggerFactory.getLogger(LoginRequiredInterceptor.class);
//
//    @Resource
//    private HostHolder hostHolder;
//
//
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        logger.info("请求拦截: {}", request.getRequestURI());
//
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            Method method = handlerMethod.getMethod();
//            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
//            if (loginRequired != null && hostHolder.getUser() == null) {
//                logger.info("用户未登录，即将跳转到登录页面: {}", ForumUtil.contextPathJudge(request.getContextPath()) + "/login");
//
//                String req = request.getHeader("x-requested-with");
//                if ("XMLHttpRequest".equals(req)) {
//                    response.setContentType("application/json;charset=utf-8");
//                    PrintWriter out = response.getWriter();
//                    out.write(ForumUtil.getJSONString(1, "login pls!"));
//                } else {
//                    // 请求为网页请求
//                    response.sendRedirect(ForumUtil.contextPathJudge(request.getContextPath()) + "/login");
//                }
//
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//    }
//}