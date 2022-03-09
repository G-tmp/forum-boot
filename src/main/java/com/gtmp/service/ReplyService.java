package com.gtmp.service;

import com.gtmp.POJO.Reply;
import com.gtmp.mapper.PostMapper;
import com.gtmp.mapper.ReplyMapper;
import com.gtmp.util.RedisKeyUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {

    @Autowired
    ReplyMapper replyMapper;

    @Autowired
    PostMapper postMapper;

    @Autowired
    PostService postService;

    @Autowired
    RedisTemplate redisTemplate;


    public List<Reply> listReplyByPostId(@Param("postId") Integer postId, @Param("offset") Integer offset, @Param("limit") Integer limit){
        return replyMapper.listReplyByPostId(postId,offset,limit);
    }


    public List<Reply> listReplyByUserId(@Param("userid") Integer userid){
        return replyMapper.listReplyByUserId(userid);
    }


    public Integer insertReply(Reply reply){
        String key = RedisKeyUtil.getPostKey(reply.getPostId());
//        if (redisTemplate.hasKey(key)){
//            postService.incrementReplyCount(reply.getPostId());
//            postService.updateLastModifyTime(reply.getPostId(),reply.getCreateTime().getTime());
//        }
        return  postMapper.increaseReplyCount(reply.getPostId()) & replyMapper.insertReply(reply) ;
    }


    public Reply selectReplyById(Integer id){
        return replyMapper.selectReplyById(id);
    }


    public Integer countReplyByPostId(@Param("postId")Integer postId){
        return replyMapper.countReplyByPostId(postId);
    }

    public Integer deleteReply(@Param("id")Integer id){
        return replyMapper.deleteReply(id);
    }
}
