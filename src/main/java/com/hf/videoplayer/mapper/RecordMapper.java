package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.Record;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMapper {

    /**
     *
     * @param record
     * @return 受影响的行数
     */
    Integer insert(Record record);

    /**
     *
     * @param uid 用户id
     * @param vid 视频id
     * @return 该用户该视频下的记录
     */
    Record findRecordByUidAndVid(String uid,String vid);

    /**
     *
     * @param record
     * @return 受影响的行数
     */
    Integer update(Record record);

}
