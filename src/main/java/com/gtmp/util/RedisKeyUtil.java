package com.gtmp.util;

public class RedisKeyUtil {

    private static final String SPLIT = ":";

    //点赞 key 前缀
//    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    private static final String PREFIX_USER_LIKE = "like:user";
    private static final String PREFIX_LIKE = "like";

    //关注 key 前缀
    private static final String PREFIX_FOLLOWING = "following";
    private static final String PREFIX_FOLLOWER = "follower";

    // 验证码前缀
    private static final String PREFIX_KAPTCHA = "kaptcha";

    // 登录凭证
    private static final String PREFIX_TICKET = "ticket";

    // 对用户做缓存
    private static final String PREFIX_USER = "user";

    // 板块
    private static final String PREFIX_BOARD = "board";

    private static final String PREFIX_POST = "post";

    // 注册激活吗
    private static final String PREFIX_ACTIVATION = "activation";

    private static final String PREFIX_TIME_RANK = "time";

    private static final String PREFIX_SCORE_RANK = "score";


    /**
     * 某个实体的赞
     * like:entity:entityType:entityId -> set(userId)
     */
    public static String getEntityLikeKey(String objectType, int objectId) {
        return PREFIX_LIKE + SPLIT + objectType + SPLIT + objectId;
    }

    /**
     * 某个用户的赞
     * like:user:user:Id -> int
     *
     * @param userId
     * @return
     */
    public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }

    /**
     * 某个用户关注的实体
     * following:userId:entityType ->  zset(entityId, now)
     */
    public static String getFollowingKey(String objectType, int entityId) {
        return PREFIX_FOLLOWING + SPLIT + objectType + SPLIT + entityId;
    }

    /**
     * 某个实体拥有的粉丝
     * follower:entityType:entityId -> zset(userId, now)
     */
    public static String getFollowerKey(String objectType, int toUserId) {
        return PREFIX_FOLLOWER + SPLIT + objectType + SPLIT + toUserId;
    }


    public static String getKaptchaKey(String owner) {
        return PREFIX_KAPTCHA + SPLIT + owner;
    }


    public static String getTicketKey(String ticket) {
        return PREFIX_TICKET + SPLIT + ticket;
    }


    public static String getUserKey(int userId) {
        return PREFIX_USER + SPLIT + userId;
    }

    public static String getPostKey(int postId) {
        return PREFIX_POST + SPLIT + postId;
    }


    public static String getBoardKey(String boardName) {
        return PREFIX_BOARD + SPLIT + boardName;
    }

    public static String getActivationKey(String activation) {
        return PREFIX_ACTIVATION + SPLIT + activation;
    }

    public static String getTimeRankKey(String object) {
        return PREFIX_TIME_RANK + SPLIT + object;
    }

    public static String getScoreRankKey(String object) {
        return PREFIX_SCORE_RANK + SPLIT + object;
    }


}