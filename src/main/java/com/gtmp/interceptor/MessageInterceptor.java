//package com.gtmp.interceptor;
//
//import com.gtmp.POJO.User;
//import com.gtmp.service.MessageService;
//import com.gtmp.service.NotifyService;
//import com.gtmp.util.HostHolder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class MessageInterceptor implements HandlerInterceptor {
//
//    @Resource
//    HostHolder hostHolder;
//
//    @Resource
//    MessageService messageService;
//
//    @Autowired
//    NotifyService notifyService;
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        User user = hostHolder.getUser();
//        if (user != null && modelAndView != null) {
////            int letterUnreadCount = messageService.selectLetterUnreadCount(user.getId(), null);
////            int noticeUnreadCount = messageService.selectNoticeUnreadCount(user.getId(), null);
//            int unreadCount = notifyService.selectNotifyCountUnread(user.getId(),null);
//            modelAndView.addObject("unreadCount", unreadCount);
//        }
//
//    }
//}