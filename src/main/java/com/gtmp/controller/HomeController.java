package com.gtmp.controller;


import com.gtmp.POJO.Board;
import com.gtmp.POJO.Post;
import com.gtmp.POJO.User;
import com.gtmp.enums.ObjectTypeEnum;
import com.gtmp.service.BoardServer;
import com.gtmp.service.LikeService;
import com.gtmp.service.PostService;
import com.gtmp.service.UserService;
import com.gtmp.util.ForumConstant;
import com.gtmp.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController implements ForumConstant {
    @Resource
    PostService postService;

    @Resource
    UserService userService;

    @Autowired
    BoardServer boardServer;

    @Resource
    LikeService likeService;


    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String toIndexPage(Model model, @RequestParam(value = "page", required = false) Integer cur) {
        Page page = new Page();
        page.setProperties(5, cur, postService.selectPostCount());
        page.setPath("/index");

        List<Post> list = postService.listPostsNew((page.getCur() - 1) * page.getSize(), page.getSize());
        List<Map<String, Object>> posts = new ArrayList<>();

        if (list != null) {
            for (Post post : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);

                User user = userService.selectUserById(post.getUserId());
                map.put("user", user);

                Board board = boardServer.selectBoardById(post.getBoardId());
                map.put("board", board);
                // 点赞数量
                long likeCount = likeService.findEntityLikeCount(ObjectTypeEnum.POST, post.getId());
                map.put("likeCount", likeCount);

                posts.add(map);
            }
        }

        model.addAttribute("page", page);
        model.addAttribute("posts", posts);
        return "bro/home";
    }


    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String getErrorPage() {
        return "error/500";
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String notFound() {
        return "error/404";
    }

}
