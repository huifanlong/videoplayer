package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.Record;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TimeRecordMapperTests {
    @Autowired
    private RecordMapper recordMapper;

    @Test
    public void findTimeRecordsByVid(){
        Record[] records = recordMapper.findTimeRecordsByVid(2);
        System.out.println(records.toString());
//        for(int i = 0;i<records.length;i++){
//            System.out.println(records[i].getUid());
//            System.out.println(records[i].getTime());
//        }
    }
    @Test
    public void findRecordByVidAndUid(){
        Record[] records = recordMapper.findRecordByUidAndVid("kakaki",2);
        System.out.println(records[records.length-1].getWatchTimes());
    }
}
