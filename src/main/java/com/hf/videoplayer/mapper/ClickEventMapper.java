package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.ClickEvent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClickEventMapper {
    /**
     *
     * @param clickEvent
     * @return 受影响的行数
     */
    Integer insert(ClickEvent clickEvent);

    ClickEvent[] findEventsByUidAndVid(String uid,Integer vid);
}
