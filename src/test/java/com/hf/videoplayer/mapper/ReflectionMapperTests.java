package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.Reflection;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.crypto.Data;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReflectionMapperTests {
    @Autowired
    private ReflectionMapper reflectionMapper;

    @Test
    public void insert(){
        Reflection reflection = new Reflection();
        reflection.setUid("uid");
        reflection.setVid(1);
        reflection.setTitle("title");
        reflection.setReflection("reflection");
        Date time = new Date();
        SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        reflection.setTime(ft2.format(time));
        reflectionMapper.insert(reflection);
    }

    @Test
    public void delete(){
//        reflectionMapper.delete("uid","2022-08-28 14:58:07");
    }

    @Test
    public void findReflectionByUidAndTime(){
        reflectionMapper.findReflectionByUidAndTime("uid","2022-08-28 15:04:58");
    }
    @Test
    public void findReflectionByUid(){
        Reflection[] reflections = reflectionMapper.findReflectionsByUid("uid");
        System.out.println(reflections.length);
    }
    @Test
    public void findReflectionByUidAndVid(){
        Reflection[] reflections = reflectionMapper.findReflectionsByUidAndVid("uid",1);
        System.out.println(reflections.length);
    }
}
