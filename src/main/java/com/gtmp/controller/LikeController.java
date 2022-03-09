package com.gtmp.controller;

import com.gtmp.POJO.User;
import com.gtmp.enums.ObjectTypeEnum;
import com.gtmp.service.LikeService;
import com.gtmp.util.ForumConstant;
import com.gtmp.util.JsonRes;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController implements ForumConstant {

    @Autowired
    LikeService likeService;

//    @Resource
//    EventProducer eventProducer;



    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @ResponseBody
//    public String like(int entityType, int entityId, int toUserId) {
    public JsonRes like(@RequestBody Map<String,Object> param) {
        Integer entityType = (Integer) param.get("entityType");
        Integer entityId = (Integer) param.get("entityId");
        Integer toUserId = (Integer) param.get("toUserId");
        Subject subject = SecurityUtils.getSubject();
        User loginUser = (User) subject.getPrincipal();

        if (entityType == 1 && loginUser.getId() == entityId) {
            return new JsonRes().setCode(JsonRes.ERROR_CODE).setMsg("你脸皮真厚");
        }


        ObjectTypeEnum objectType = ObjectTypeEnum.codeOf(entityType);

        // 点赞
        likeService.like(loginUser.getId(), objectType, entityId, toUserId);
        // 数量
        long likeCount = likeService.findEntityLikeCount(objectType, entityId);
        // 状态
        boolean ifLike = likeService.findEntityLikeStatus(loginUser.getId(), objectType, entityId);
        // 返回结果
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("isLiked", ifLike);

        // 触发点赞事件
//        if (likeStatus == 1) {
//            Event event = new Event()
//                    .setTopic(TOPIC_LIKE)
//                    .setUserId(hostHolder.getUser().getId())
//                    .setEntityType(entityType)
//                    .setEntityId(entityId)
//                    .setToUserId(toUserId)
//                    .setData("postId", postId);
//            eventProducer.fireEvent(event);
//        }

        return new JsonRes().setCode(JsonRes.SUCCESS_CODE).setMsg(JsonRes.SUCCESS_MSG).setData(map);
    }
}
