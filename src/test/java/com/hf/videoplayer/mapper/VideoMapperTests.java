package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.User;
import com.hf.videoplayer.entity.Video;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VideoMapperTests {
    @Autowired
    private VideoMapper videoMapper;

    @Test
    public void insert(){
        Video video = new Video();
        video.setCid(1);
        video.setVideoName("ccc");
        video.setSrc("ggg");
        Integer rows = videoMapper.insert(video);
        System.out.println(rows);
    }

    @Test
    public void findByVideoName(){
        Video video = videoMapper.findByVideoName("ccc");
        System.out.println(video);
    }

    @Test
    public void findByVideoId(){
        Video video = videoMapper.findByVideoId(1);
        System.out.println(video);
    }

    @Test
    public void updateLikeNumbersByVideoId(){
        Integer rows = videoMapper.updateLikeNumbersByVideoId(1,1);
    }

    @Test
    public void updateCollectNumbersByVideoId(){
        Integer rows = videoMapper.updateCollectNumbersByVideoId(1,1);
        System.out.println(rows);
    }
}
