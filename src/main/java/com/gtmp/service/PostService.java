package com.gtmp.service;


import com.gtmp.POJO.Post;
import com.gtmp.mapper.PostMapper;
import com.gtmp.util.RedisKeyUtil;
import com.gtmp.util.RedisOpsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class PostService {
    @Autowired
    PostMapper postMapper;

    @Autowired
    RedisTemplate redisTemplate;


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 一天的时间戳
    private long timestampOneDay = 86400 * 1000;


    private boolean isExpert(long time) {
        if (System.currentTimeMillis() > time + timestampOneDay) {
            return true;
        }

        return false;
    }

    private boolean addCache(Post post) {
        if (post == null || isExpert(post.getCreateTime().getTime())) {
            return false;
        }
        return addCache(post, 3, TimeUnit.DAYS);
    }

    private boolean addCache(Post post, long expertTime, TimeUnit unit) {
        String key = RedisKeyUtil.getPostKey(post.getId());
        redisTemplate.opsForHash().put(key, "title", post.getTitle());
        redisTemplate.opsForHash().put(key, "content", post.getContent());
        redisTemplate.opsForHash().put(key, "createTime", post.getCreateTime().getTime());
        redisTemplate.opsForHash().put(key, "lastModifiedTime", post.getLastModifiedTime().getTime());
        redisTemplate.opsForHash().put(key, "userId", post.getUserId());
        redisTemplate.opsForHash().put(key, "boardId", post.getBoardId());
        redisTemplate.opsForHash().put(key, "status", post.getStatus());
        redisTemplate.opsForHash().put(key, "replyCount", post.getReplyCount());
        redisTemplate.opsForHash().put(key, "score", post.getScore());
        redisTemplate.expire(key, expertTime, unit);

        return true;
    }

    private Post getCache(Integer id) {
        String key = RedisKeyUtil.getPostKey(id);
        if (!redisTemplate.hasKey(key))
            return null;

        redisTemplate.opsForHash().entries(key);
        Post post = new Post()
                .setId(id)
                .setTitle((String) RedisOpsUtil.hget(key, "title"))
                .setContent((String) RedisOpsUtil.hget(key, "content"))
                .setBoardId((Integer) RedisOpsUtil.hget(key, "boardId"))
                .setUserId((Integer) RedisOpsUtil.hget(key, "userId"))
                .setCreateTime(new Date((Long) RedisOpsUtil.hget(key, "createTime")))
                .setLastModifiedTime(new Date((Long) RedisOpsUtil.hget(key, "lastModifiedTime")))
                .setScore((Double) RedisOpsUtil.hget(key, "score"))
                .setReplyCount((Integer) RedisOpsUtil.hget(key, "replyCount"))
                .setStatus((Integer) RedisOpsUtil.hget(key, "status"));
        return post;
    }

    private void delCache(Post post) {
        String key = RedisKeyUtil.getPostKey(post.getId());
        redisTemplate.delete(key);
    }

    private void addTimeRank(Post post) {
        String key = RedisKeyUtil.getTimeRankKey("post");
        redisTemplate.opsForZSet().add(key, post.getId(), post.getLastModifiedTime().getTime());
    }

    private void addScoreRank(Post post) {
        String key = RedisKeyUtil.getScoreRankKey("post");
        redisTemplate.opsForZSet().add(key, post.getId(), post.getLastModifiedTime().getTime());
    }

    void updateLastModifyTime(Integer postId, double timestamp) {
        String key = RedisKeyUtil.getTimeRankKey("post");
        String keyPost = RedisKeyUtil.getPostKey(postId);

        redisTemplate.opsForHash().put(keyPost, "lastModifiedTime", (long) timestamp);
        redisTemplate.opsForZSet().add(key, postId, timestamp);
    }

    // TODO
    void updateScore(Integer postId, double score) {
        String key = RedisKeyUtil.getScoreRankKey("post");

        redisTemplate.opsForZSet().incrementScore(key, postId, score);
    }

    void incrementReplyCount(Integer postId) {
        String key = RedisKeyUtil.getPostKey(postId);
        calculateScore();
        redisTemplate.opsForHash().increment(key, "replyCount", 1);
    }

    // TODO
    private double calculateScore() {

        return 0;
    }

    /********************************************************
     *                                                      *
     *                                                      *
     *                                                      *
     *                                                      *
     ********************************************************/


    public List<Post> listPostsNew(int offset, int limit) {
        String key = RedisKeyUtil.getTimeRankKey("post");
        Set cacheSet = redisTemplate.opsForZSet().reverseRange(key, offset, limit);

        // from db
        if (cacheSet == null || cacheSet.size() == 0 || cacheSet.size() < limit) {
            List<Post> list = postMapper.listPostsOrderByModifyTime(offset, limit);
            if (list != null && list.size() > 0) {
                for (Post e : list) {
                    if (addCache(e))
                        addTimeRank(e);
                }
            }

            return list;
        }

//        else if (cacheSet.size() < limit) {
//            // 从 db 查询 cache 中缺少的部分，并不是全部从 db 查
//            int remain = limit - cacheSet.size();
//            List<Post> list = postMapper.listPostsOrderByModifyTime(cacheSet.size() + offset, remain);
//
//            for (Post e : list) {
//                if (addCache(e))
//                    addTimeRank(e);
//            }
//
//            List<Post> res = new ArrayList<>();
//            for (Object e : cacheSet) {
//                Post post = getCache((Integer) e);
//                res.add(post);
//            }
//            res.addAll(list);
//            // TODO sort
////            Collections.sort(res, new Comparator<Post>() {
////                @Override
////                public int compare(Post p1, Post p2) {
////                    return p1.getLastModifiedTime().compareTo(p2.getLastModifiedTime());
////                }
////            });
//
////            res.sort((Post h1, Post h2) -> h1.getLastModifiedTime().compareTo(h2.getLastModifiedTime()));
//            return res;
//        }

        // from cache
        List<Post> list = new ArrayList<>();
        for (Object e : cacheSet) {
            Post post = getCache((Integer) e);
            list.add(post);
        }

        return list;
    }


    public List<Post> listPostsByUserId(int userId, int offset, int limit) {
        return postMapper.listPostsByUserId(userId, offset, limit);
    }

    public List<Post> listPostsByBoardId(int boardId, int offset, int limit) {
        return postMapper.listPostsByBoardId(boardId, offset, limit);
    }

    public Integer selectPostCount() {
        return postMapper.selectPostCount();
    }

    public Integer selectPostCountByBoardId(int boardId) {
        return postMapper.selectPostCountByBoardId(boardId);
    }

    public Integer insertPost(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        // 转义 HTML
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));

        Integer res = postMapper.insertPost(post);
        String key = RedisKeyUtil.getPostKey(post.getId());

        addCache(post);
        addTimeRank(post);
        addScoreRank(post);


        return res;
    }


    public Post selectPostById(int id) {
        Post cache = getCache(id);

        if (cache != null) {
            return cache;
        }

        Post post = postMapper.selectPostById(id);
        addCache(post);

        return post;
    }


}