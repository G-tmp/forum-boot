package com.gtmp.service;


import com.gtmp.POJO.Notify;
import com.gtmp.mapper.NotifyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class NotifyService {

    @Autowired
    private NotifyMapper notifyMapper;


    public Integer insertNotify(Notify notify) {
        notify.setContent(HtmlUtils.htmlEscape(notify.getContent()));
        return notifyMapper.insertNotify(notify);
    }

    public Integer selectNotifyCountUnread(Integer toUserId, @Nullable String action) {
        return notifyMapper.selectNotifyCountUnread(toUserId, action);
    }

    public Integer selectNotifyCount(Integer toUserId, @Nullable String action) {
        return notifyMapper.selectNotifyCount(toUserId, action);
    }

    public Integer updateStatus(Integer toUserId, Integer status, @Nullable String action, @Nullable String objectType) {
        return notifyMapper.updateStatus(toUserId, status, action, objectType);
    }

    public List<Notify> listAllNotify(Integer toUserId, @Nullable String action, int offset, int limit) {
        return notifyMapper.listAllNotify(toUserId, action, offset, limit);
    }

    public List<Notify> listAllNotifyUnread(Integer toUserId, @Nullable String action, int offset, int limit) {
        return notifyMapper.listAllNotifyUnread(toUserId, action, offset, limit);
    }

    public Notify selectNotifyRecent(Integer toUserId, @Nullable String action) {
        return notifyMapper.selectNotifyRecent(toUserId, action);
    }
}
