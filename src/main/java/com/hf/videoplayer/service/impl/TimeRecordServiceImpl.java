package com.hf.videoplayer.service.impl;

import com.hf.videoplayer.entity.Record;
import com.hf.videoplayer.mapper.RecordMapper;
import com.hf.videoplayer.service.ITimeRecordService;
import com.hf.videoplayer.service.ex.VideoWatchingTimeRecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeRecordServiceImpl implements ITimeRecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public Record[] findTimeRecordsByVid(Integer vid) {
        Record[] records = recordMapper.findTimeRecordsByVid(vid);
        if(records.length==0){
            throw new VideoWatchingTimeRecordNotFoundException();
        }else{
            return records;
        }
    }
}
