package com.gtmp.mapper;


import com.gtmp.POJO.Notify;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.Nullable;

import java.util.List;

@Mapper
public interface NotifyMapper {

    Integer insertNotify(Notify notify);

    Integer selectNotifyCountUnread(Integer toUserId, @Nullable String action);

    Integer selectNotifyCount(Integer toUserId, @Nullable String action);

    Integer updateStatus(Integer toUserId, Integer status, @Nullable String action, @Nullable String objectType);

    Notify selectNotifyRecent(Integer toUserId, @Nullable String action);

    List<Notify> listAllNotify(Integer toUserId, @Nullable String action, Integer offset, Integer limit);

    List<Notify> listAllNotifyUnread(Integer toUserId, @Nullable String action, Integer offset, Integer limit);
}
