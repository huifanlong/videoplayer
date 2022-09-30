package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.MultipleIntelligence;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MultipleIntelliMapper {
    /**
     * 插入一条学生的多元智能数据
     * @param multipleIntelligence 多元智能的全部数据
     * @return 受影响的行数
     */
    Integer insertMultiple(MultipleIntelligence multipleIntelligence);
}