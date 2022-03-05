package com.gtmp.mapper;

import com.gtmp.POJO.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {

//    List<Reply> listReplyByPostId(Integer pid);


    List<Reply> listReplyByPostId(@Param("postId") Integer postId, @Param("offset") Integer offset, @Param("limit") Integer limit);


    List<Reply> listReplyByUserId(@Param("userid") Integer userid);


    Integer insertReply(Reply reply);


    Integer selectReplyCountByPostId(@Param("postId")Integer postId);


    Reply selectReplyById(Integer id);


    Integer deleteReply(@Param("id")Integer id);

}
