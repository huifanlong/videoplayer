package com.hf.videoplayer.service;

import com.hf.videoplayer.entity.QuizRecord;
import com.hf.videoplayer.entity.Record;

public interface ITimeRecordService {
    /**
     * 根据vid，查找某视频下的所有学生time_record记录
     * @param vid
     */
    Record[] findTimeRecordsByVid(Integer vid);
}
