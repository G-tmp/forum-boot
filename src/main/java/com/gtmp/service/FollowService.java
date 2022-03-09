//package com.gtmp.service;
//
//import com.gtmp.POJO.User;
//import com.gtmp.enums.ObjectTypeEnum;
//import com.gtmp.util.ForumConstant;
//import com.gtmp.util.RedisKeyUtil;
//import org.springframework.dao.DataAccessException;
//import org.springframework.data.redis.core.RedisOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.SessionCallback;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.*;
//
//
//@Service
//public class FollowService implements ForumConstant {
//    @Resource
//    RedisTemplate redisTemplate;
//
//    @Resource
//    UserService userService;
//
//
//    public void follow(int userId, ObjectTypeEnum objectType, int objectId) {
//        // 一个方法有两次操作请求，加入事务
//        redisTemplate.execute(new SessionCallback<Object>() {
//            @Override
//            public Object execute(RedisOperations operations) throws DataAccessException {
//                String followingKey = RedisKeyUtil.getFollowingKey(objectType.getName(), userId);
//                String followerKey = RedisKeyUtil.getFollowerKey(objectType.getName(), objectId);
//
//                long now = System.currentTimeMillis();
//                boolean isFollowing = operations.opsForZSet().add(followingKey, objectId, now);
//                boolean isFollower = operations.opsForZSet().add(followerKey, userId, now);
//
//                operations.multi();
//
//                if (!isFollowing) {
//                    operations.opsForZSet().remove(followingKey, objectId);
//                }
//
//                if (!isFollower) {
//                    operations.opsForZSet().remove(followerKey, userId);
//                }
//
//                return operations.exec();
//            }
//        });
//    }
//
////    public void unFollow(int userId, ObjectTypeEnum objectType, int objectId) {
////        // 一个方法有两次操作请求，加入事务
////        redisTemplate.execute(new SessionCallback<Object>() {
////            @Override
////            public Object execute(RedisOperations operations) throws DataAccessException {
////                String followingKey = RedisKeyUtil.getFollowingKey(objectType.getName(), userId);
////                String followerKey = RedisKeyUtil.getFollowerKey(objectType.getName(), objectId);
////
//////                long isExist = redisTemplate.opsForZSet().rank(followingKey, objectId);
////                operations.multi();
////
////                operations.opsForZSet().remove(followingKey, objectId);
////                operations.opsForZSet().remove(followerKey, userId);
////
////                return operations.exec();
////            }
////        });
////    }
//
//    public long findFollowingCount(ObjectTypeEnum objectType, int userId) {
//        String followingKey = RedisKeyUtil.getFollowingKey(objectType.getName(), userId);
//        return redisTemplate.opsForZSet().zCard(followingKey);
//    }
//
//    public long findFollowerCount(ObjectTypeEnum objectType, int entityId) {
//        String followerKey = RedisKeyUtil.getFollowerKey(objectType.getName(), entityId);
//        // 即使 key 不存在，返回 0
//        return redisTemplate.opsForZSet().zCard(followerKey);
//    }
//
//    public boolean hasFollowed(int userId, ObjectTypeEnum objectType, int entityId) {
//        String followingKey = RedisKeyUtil.getFollowingKey(objectType.getName(), userId);
//        return redisTemplate.opsForZSet().score(followingKey, entityId) != null;
//    }
//
//    public List<Map<String, Object>> findFollowingList(ObjectTypeEnum objectType, int entityId, int offset, int limit) {
//        String followingKey = RedisKeyUtil.getFollowingKey(objectType.getName(), entityId);
//        Set<Integer> targetIds = redisTemplate.opsForZSet().reverseRange(followingKey, offset, offset + limit - 1);
//
//        if (targetIds == null) {
//            return null;
//        }
//
//        List<Map<String, Object>> list = new ArrayList<>();
//        for (Integer targetId : targetIds) {
//            Map<String, Object> map = new HashMap<>();
//
//            User user = userService.selectUserById(targetId);
//            map.put("user", user);
//
//            Double score = redisTemplate.opsForZSet().score(followingKey, targetId);
//            map.put("followTime", new Date(score.longValue()));
//
//            list.add(map);
//        }
//        return list;
//    }
//
//    public List<Map<String, Object>> findFollowerList(int userId, int offset, int limit) {
//        String followerKey = RedisKeyUtil.getFollowerKey(ObjectTypeEnum.USER.getName(), userId);
//        Set<Integer> targetIds = redisTemplate.opsForZSet().reverseRange(followerKey, offset, offset + limit - 1);
//
//        if (targetIds == null) {
//            return null;
//        }
//
//        List<Map<String, Object>> list = new ArrayList<>();
//        for (Integer targetId : targetIds) {
//            Map<String, Object> map = new HashMap<>();
//
//            User user = userService.selectUserById(targetId);
//            map.put("user", user);
//
//            Double score = redisTemplate.opsForZSet().score(followerKey, targetId);
//            map.put("followTime", new Date(score.longValue()));
//
//            list.add(map);
//        }
//        return list;
//    }
//}
