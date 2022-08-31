package com.hf.videoplayer.service.impl;

import com.hf.videoplayer.entity.ClickEvent;
import com.hf.videoplayer.mapper.ClickEventMapper;
import com.hf.videoplayer.service.IClickEventService;
import com.hf.videoplayer.service.ex.ClickEventCreateFialedException;
import com.hf.videoplayer.service.ex.UVPairIsNullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClickEventServiceImpl implements IClickEventService {
    @Autowired
    private ClickEventMapper clickEventMapper;

    @Override
    public void createClickEvent(ClickEvent clickEvent) {
        Integer result = clickEventMapper.insert(clickEvent);
        if(result != 1){
            throw new ClickEventCreateFialedException("点击流诗句创建失败");
        }
    }

    @Override
    public ClickEvent[] findUVPair(String uid, Integer vid) {
        ClickEvent[] clickEvents = clickEventMapper.findEventsByUidAndVid(uid,vid);
        if(clickEvents.length == 0){
            throw new UVPairIsNullException("该用户在该视频下尚未产生点击流记录");
        }
        return clickEvents;
    }
}
