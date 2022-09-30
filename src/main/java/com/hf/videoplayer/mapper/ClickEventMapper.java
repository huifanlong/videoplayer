package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.ClickEvent;
import com.hf.videoplayer.entity.MultipleIntelligence;
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

    Integer insertMultiple(MultipleIntelligence multipleIntelligence);

    MultipleIntelligence[] findMultiple(String uid);
}
