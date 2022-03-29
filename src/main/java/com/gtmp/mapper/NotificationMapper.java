package com.gtmp.mapper;

import com.gtmp.POJO.Notification;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationMapper {

    public Integer insertNotification(Notification notification);

    public List<Notification> listNotificationByUserId(Integer userId);

    public int countUnreadNotificationByUserId(Integer userId);

    public void readNotification(Integer id);

    public void readAllNotificationByUser(Integer userId);
}
