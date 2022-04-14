package com.hf.videoplayer.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VideoServiceTests {
    @Autowired
    private IVideoService iVideoService;

    @Test
    public void addLikeNumbersByVideoId(){
        iVideoService.addLikeNumbersByVideoId(2);
    }
    @Test
    public void addLikeCollectionByVideoId(){
        iVideoService.addCollectNumbersByVideoId(3);
    }
}
