package com.gtmp.mapper;

import com.gtmp.POJO.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {

//    List<Post> listPostsOrderByCreateTime(int offset,int limit);

    //    List<Post> listPostsOrderByScore(int offset,int limit);

    List<Post> listPostsOrderByModifyTime(int offset,int limit);

    List<Post> listPostsByUserId(int userId, int offset, int limit);

    List<Post> listPostsByBoardId(int boardId, int offset, int limit);

    Integer countPost();

    Integer countPostByBoardId(@Param("boardId") int boardId);

    Post selectPostById(@Param("id")int id);

    Integer insertPost(Post post);

    Integer increaseReplyCount(@Param("id") int id);

//    Integer deletePostById(@Param("id")int id);
}
