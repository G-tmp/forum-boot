//package com.gtmp.util;
//
//import com.gtmp.POJO.User;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class HostHolder {
//    /**
//     * 线程隔离
//     * 存放登录用户
//     */
//    private ThreadLocal<User> users = new ThreadLocal<>();
//
//
//
//    public void setUser(User user) {
//        users.set(user);
//    }
//
//    public User getUser() {
//        return users.get();
//    }
//
//    public void clear() {
//        users.remove();
//    }
//}
