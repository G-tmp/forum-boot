package com.gtmp.controller;


import com.gtmp.POJO.Board;
import com.gtmp.POJO.Post;
import com.gtmp.POJO.User;
import com.gtmp.enums.ObjectTypeEnum;
import com.gtmp.service.BoardServer;
import com.gtmp.service.LikeService;
import com.gtmp.service.PostService;
import com.gtmp.service.UserService;
import com.gtmp.util.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class BoardController {

    @Autowired
    private BoardServer boardServer;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;


    @RequestMapping(value = "/board/{ename}", method = RequestMethod.GET)
    private String select(Model model, @PathVariable("ename") String ename, @RequestParam(value = "page", required = false) Integer cur) {
        Board board = boardServer.selectBoardByEname(ename);

        Page page = new Page();
        page.setPath("/board/" + board.getEname());
        page.setProperties(5, cur, postService.countPostByBoardId(board.getId()));

        List<Post> posts = postService.listPostsByBoardId(board.getId(), (page.getCur() - 1) * page.getSize(), page.getSize());
        List<Map<String, Object>> list = new ArrayList<>();
        for (Post post : posts) {
            Map<String, Object> map = new HashMap<>();
            map.put("post", post);

            User user = userService.selectUserById(post.getUserId());
            map.put("user", user);

            long likeCount = likeService.findEntityLikeCount(ObjectTypeEnum.POST,post.getId());
            map.put("likeCount",likeCount);

            list.add(map);
        }

        model.addAttribute("page",page);
        model.addAttribute("board", board);
        model.addAttribute("posts", list);

        return "bro/board";
    }
}
