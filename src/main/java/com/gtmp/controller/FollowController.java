//package com.gtmp.controller;
//
//
//import com.gtmp.POJO.User;
//import com.gtmp.enums.ObjectTypeEnum;
//import com.gtmp.service.FollowService;
//import com.gtmp.service.UserService;
//import com.gtmp.util.*;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.subject.Subject;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//public class FollowController implements ForumConstant {
//
//    @Resource
//    FollowService followService;
//
//    @Resource
//    UserService userService;
//
////    @Resource
////    EventProducer eventProducer;
//
//
//    @RequestMapping(value = "/follow", method = RequestMethod.POST)
//    @ResponseBody
////    public String follow(int entityType, int entityId) {
//    public JsonRes follow(@RequestBody Map<String, Object> param) {
//        Integer entityType = (Integer) param.get("entityType");
//        Integer entityId = (Integer) param.get("entityId");
//
//        Subject subject = SecurityUtils.getSubject();
//        User loginUser = (User) subject.getPrincipal();
//        ObjectTypeEnum entity = ObjectTypeEnum.codeOf(entityType);
//        followService.follow(loginUser.getId(), entity, entityId);
//        boolean isFollowed = followService.hasFollowed(loginUser.getId(), entity, entityId);
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("isFollowed",isFollowed );
//        map.put("followerCount",followService.findFollowerCount(entity,entityId));
//
//        // TODO 消息队列
//
//        return new JsonRes().setCode(JsonRes.SUCCESS_CODE).setMsg(JsonRes.SUCCESS_MSG).setData(map);
//    }
//
//
////    @LoginRequired
////    @RequestMapping(value = "/unFollow", method = RequestMethod.POST)
////    @ResponseBody
////    public String unFollow(@RequestBody Map<String, Object> param) {
////        Integer entityType = (Integer) param.get("entityType");
////        Integer entityId = (Integer) param.get("entityId");
////
////        User user = hostHolder.getUser();
////        ObjectTypeEnum entity = ObjectTypeEnum.codeOf(entityType);
////
////        followService.unFollow(user.getId(), entity, entityId);
////        return ForumUtil.getJSONString(0, "已取消关注");
////    }
//
//
//    @RequestMapping(value = "/following/{userId}", method = RequestMethod.GET)
//    public String getFollowing(@PathVariable("userId") int userId, Page page, Model model) {
//        User loginUser = userService.selectUserById(userId);
//        if (loginUser == null) {
//            throw new RuntimeException("用户不存在");
//        }
//        model.addAttribute("user", loginUser);
//
//        page.setSize(5);
//        page.setPath("/following/" + userId);
//        page.setTotalRecord((int) followService.findFollowingCount(ObjectTypeEnum.USER, userId));
//
//        List<Map<String, Object>> userList = followService.findFollowingList(ObjectTypeEnum.USER, userId, 0, page.getSize());
//        if (userList != null) {
//            for (Map<String, Object> map : userList) {
//                User u = (User) map.get("user");
//                map.put("hasFollowed", followService.hasFollowed(loginUser.getId(),ObjectTypeEnum.USER,userId));
//            }
//        }
//        model.addAttribute("users", userList);
//
//        return "/site/following";
//    }
//
//
//    @GetMapping("/follower/{userId}")
//    public String getFollowers(@PathVariable("userId") int userId, Page page, Model model) {
//        User loginUser = userService.selectUserById(userId);
//        if (loginUser == null) {
//            throw new RuntimeException("用户不存在");
//        }
//        model.addAttribute("user", loginUser);
//
//        page.setSize(5);
//        page.setPath("/follower/" + userId);
//        page.setTotalRecord((int) followService.findFollowerCount(ObjectTypeEnum.USER, userId));
//
//        List<Map<String, Object>> userList = followService.findFollowerList(userId, 0, page.getSize());
//        if (userList != null) {
//            for (Map<String, Object> map : userList) {
//                User u = (User) map.get("user");
//                map.put("hasFollowed", followService.hasFollowed(loginUser.getId(),ObjectTypeEnum.USER,userId));
//            }
//        }
//        model.addAttribute("users", userList);
//
//        return "/site/follower";
//    }
//
////    private boolean hasFollowed(int userId) {
////        if (hostHolder.getUser() == null) {
////            return false;
////        }
////        return followService.hasFollowed(hostHolder.getUser().getId(), ObjectTypeEnum.USER, userId);
////    }
//
//
//}
