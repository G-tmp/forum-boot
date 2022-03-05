package com.gtmp.service;

import com.gtmp.POJO.Like;
import com.gtmp.enums.ObjectTypeEnum;
import com.gtmp.mapper.LikeMapper;
import com.gtmp.util.ForumConstant;
import com.gtmp.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class LikeService implements ForumConstant {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    LikeMapper likeMapper;


    Integer insertOrUpdateLike(Like like) {
        return likeMapper.insertOrUpdateLike(like);
    }


    public void like(int userId, ObjectTypeEnum objectType, int entityId, int toUserId) {
        // 加入事务
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String entityLikeKey = RedisKeyUtil.getEntityLikeKey(objectType.getName(), entityId);
//                String userLikeKey = RedisKeyUtil.getUserLikeKey(toUserId);

                // 查询操作要在事务开启之前执行，否则在事务中不会立即执行。
                Boolean isMember = operations.opsForSet().isMember(entityLikeKey, userId);

                // 开启事务
                operations.multi();

                if (isMember) {
                    // unlike
                    operations.opsForSet().remove(entityLikeKey, userId);
                } else {
                    // like
                    operations.opsForSet().add(entityLikeKey, userId);
                }

                // execute
                return operations.exec();
            }
        });
    }

    public long findEntityLikeCount(ObjectTypeEnum objectType, int objectId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(objectType.getName(), objectId);
        return redisTemplate.opsForSet().size(entityLikeKey);
//        return redisTemplate.opsForZSet().zCard(entityLikeKey);
    }


    public boolean findEntityLikeStatus(int userId, ObjectTypeEnum objectType, int objectId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(objectType.getName(), objectId);
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId);
    }


}
