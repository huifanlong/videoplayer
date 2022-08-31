package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.ClickEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClickEventMapperTests {
    @Autowired
    private ClickEventMapper clickEventMapper;

    @Test
    public void insert(){
        SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ClickEvent clickEvent = new ClickEvent();
        clickEvent.setUid("uid");
        clickEvent.setVid(1);
        clickEvent.setEvent("event");
        clickEvent.setPosition(255);
        clickEvent.setTime(ft2.format(new Date()));
        clickEvent.setState("state");
        clickEvent.setRate(1.5);
        clickEventMapper.insert(clickEvent);
    }
    @Test
    public void findByUidAndVid(){
        ClickEvent[] clickEvents = clickEventMapper.findEventsByUidAndVid("uid",1);
        System.out.println(clickEvents.length);
    }
}
