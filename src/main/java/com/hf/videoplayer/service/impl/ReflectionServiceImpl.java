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
         /*
        如果该时刻的笔记已经存在，那么修改其内容
         */
        Reflection result = reflectionMapper.findReflectionByUidAndTime(reflection.getUid(),reflection.getTime());
        if(result != null){
//            throw new NoteThisSecondAlreadyExistException("该时刻已经有笔记，请前去修改而不是创建");其实不是可以直接在这里修改吗？
            reflectionMapper.updateReflection(reflection.getReflection(),reflection.getTitle(),reflection.getTime(),reflection.getUid(),reflection.getTime());
        }
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
           reflections[i].setId(null);
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
    public Integer updateReflection(Reflection reflection,String fromDbsTime) throws ParseException {
        SimpleDateFormat ft1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        reflection.setTime(ft2.format(ft1.parse(reflection.getTime())));//格式转换
        System.out.println(reflection.toString());
        System.out.println(fromDbsTime);
        Integer result = reflectionMapper.updateReflection(reflection.getReflection(),reflection.getTitle(),reflection.getTime(),reflection.getUid(), fromDbsTime);
        if(result != 1){
            throw new NoteNotExistException("更新失败");
        }
        return null;
    }
}
