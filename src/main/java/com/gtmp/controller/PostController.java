package com.gtmp.controller;


import com.gtmp.POJO.*;
import com.gtmp.enums.ObjectTypeEnum;
import com.gtmp.service.*;
import com.gtmp.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RequestMapping("post/")
@Controller
public class PostController implements ForumConstant {
    @Autowired
    PostService postService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @Autowired
    LikeService likeService;


//    @Resource
//    EventProducer eventProducer;

    @Autowired
    BoardServer boardServer;

    @Autowired
    ReplyService replyService;


    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public JsonRes insertPost(@RequestBody Map<String, Object> dataMap) {
//        public String insertPost(int postId, String title, String content, int boardId) {
        String title = (String) dataMap.get("title");
        String content = (String) dataMap.get("content");
        Integer boardId = Integer.valueOf((String) dataMap.get("boardId"));

        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute("loginUser");

        Post post = new Post();
        post.setUserId(user.getId())
                .setBoardId(boardId)
                .setTitle(title)
                .setContent(content)
                .setReplyCount(0)
                .setScore(0.0)
                .setStatus(0)
                .setCreateTime(new Date())
                .setLastModifiedTime(new Date());

        postService.insertPost(post);

        // 触发发帖事件
//        Event event = new Event()
//                .setTopic(TOPIC_PUBLISH)
//                .setUserId(user.getId())
//                .setEntityType(ENTITY_TYPE_POST)
//                .setEntityId(post.getId());
//        eventProducer.fireEvent(event);

        // 报错异常情况，后续统一处理
        return new JsonRes().setCode(JsonRes.SUCCESS_CODE).setMsg("帖子发布成功!");
    }


    @RequestMapping(value = "/{postId}", method = RequestMethod.GET)
    public String selectPostById(Model model, @PathVariable("postId") Integer postId, @RequestParam(value = "page", required = false) Integer cur) {
        User loginUser = hostHolder.getUser();
        // 帖子
        Post post = postService.selectPostById(postId);
        // 作者
        User postUser = userService.selectUserById(post.getUserId());
        // board
        Board board = boardServer.selectBoardById(post.getBoardId());
        // 点赞数量
        long likeCount = likeService.findEntityLikeCount(ObjectTypeEnum.POST, postId);
        //点赞状态
        boolean ifLike = false;
        if (loginUser != null)
            ifLike = likeService.findEntityLikeStatus(loginUser.getId(), ObjectTypeEnum.POST, postId);

        // 评论分页
        Page page = new Page();
        page.setProperties(5, cur, replyService.selectReplyCountByPostId(postId));
        page.setPath("/post/" + postId);


        List<Reply> replyList = replyService.listReplyByPostId(postId, (page.getCur() - 1) * page.getSize(), page.getSize());

        // 评论列表
        List<Map<String, Object>> comments = new ArrayList<>();
        if (replyList != null) {
            for (Reply reply : replyList) {
                Map<String, Object> map = new HashMap<>();
                map.put("reply", reply);

                User user = userService.selectUserById(reply.getUserId());
                map.put("user", user);

                long likeCounts = likeService.findEntityLikeCount(ObjectTypeEnum.REPLY, reply.getId());
                map.put("likeCount", likeCounts);

                if (loginUser != null) {
                    boolean ifLikes = likeService.findEntityLikeStatus(loginUser.getId(), ObjectTypeEnum.REPLY, reply.getId());
                    map.put("ifLike", ifLikes);
                } else {
                    map.put("ifLike", false);
                }

                comments.add(map);
            }
        }
        model.addAttribute("post", post);
        model.addAttribute("user", postUser);
        model.addAttribute("board", board);
        model.addAttribute("page", page);
        model.addAttribute("comments", comments);
        model.addAttribute("likeCount", likeCount);
        model.addAttribute("ifLike", ifLike);

        return "bro/post";
    }
}
