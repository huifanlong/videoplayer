package com.hf.videoplayer.service.impl;

import com.hf.videoplayer.entity.Reflection;
import com.hf.videoplayer.mapper.ReflectionMapper;
import com.hf.videoplayer.service.IReflectionService;
import com.hf.videoplayer.service.ex.NoteCreateFailedException;
import com.hf.videoplayer.service.ex.NoteDeleteFailedException;
import com.hf.videoplayer.service.ex.NoteNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ReflectionServiceImpl implements IReflectionService {
    @Autowired
    private ReflectionMapper reflectionMapper;
    @Override
    public void createReflection(Reflection reflection) {
        SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        reflection.setTime(ft2.format(new Date()));
        Integer rows = reflectionMapper.insert(reflection);
        if(rows != 1){//没有插入成功
            throw new NoteCreateFailedException("反思添加失败");
        }
    }

    @Override
    public void deleteReflections(String uid, String time) {
        Reflection reflection = reflectionMapper.findReflectionByUidAndTime(uid,time);
        if(reflection == null){
            throw new NoteNotExistException("反思不存在，删除失败");
        }
        Integer rows = reflectionMapper.delete(uid,time);
        if(rows == null){
            throw new NoteDeleteFailedException("删除失败");
        }
    }

    @Override
    public Reflection[] findReflectionsByUid(String uid) {
        Reflection[] reflections = reflectionMapper.findReflectionsByUid(uid);
        if(reflections.length == 0){
            throw new NoteNotExistException("没有笔记");
        }
        for(int i = 0 ;i < reflections.length;i++){
            reflections[i].setUid(null);
//           reflections[i].setId(null);
            reflections[i].setVid(null);
        }
        return reflections;
    }

    @Override
    public Reflection[] findReflectionsByUidAndVid(String uid, Integer vid) {
        Reflection[] reflections = reflectionMapper.findReflectionsByUidAndVid(uid,vid);
        if(reflections.length == 0){
            throw new NoteNotExistException("没有笔记");
        }
        for(int i = 0 ;i < reflections.length;i++){
            reflections[i].setUid(null);
            reflections[i].setId(null);
            reflections[i].setVid(null);
        }
        return reflections;
    }

    @Override
    public Integer updateReflection(Reflection reflection){
        SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        reflection.setTime(ft2.format(new Date()));//格式转换
//        System.out.println(reflection.toString());
//        System.out.println(fromDbsTime);
        reflection.setTime(ft2.format(new Date()));
        Integer result = reflectionMapper.updateReflection(reflection.getReflection(),reflection.getTitle(),reflection.getTime(),reflection.getId());
        if(result != 1){
            throw new NoteNotExistException("更新失败");
        }
        return null;
    }
}
