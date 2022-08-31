package com.hf.videoplayer.service;

import com.hf.videoplayer.entity.ClickEvent;

public interface IClickEventService {
    /**
     * 创建一条点击流事件
     * @param clickEvent
     */
    void createClickEvent(ClickEvent clickEvent);

    /**
     * 找到UV-Pair
     * @param uid
     * @param vid
     * @return
     */
    ClickEvent[] findUVPair(String uid,Integer vid);
}
