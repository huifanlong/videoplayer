package com.hf.videoplayer.service;

import com.hf.videoplayer.entity.Note;
import com.hf.videoplayer.entity.Reflection;

import java.text.ParseException;

public interface IReflectionService {
    /**
     * 创建一条反思
     * @param reflection
     */
    void createReflection(Reflection reflection);

    /**
     * 删除一条笔记
     * @param uid 用户id
     * @param time 时间日期
     */
    void deleteReflections(String uid,String time);

    /**
     * 查询某用户所有笔记
     * @param uid
     * @return 返回数组长度为0 代表没有
     */
    Reflection[] findReflectionsByUid(String uid);
    /**
     * 查询某用户某视频的所有笔记
     * @param uid
     * @param vid
     * @return 返回数组长度为0 代表没有
     */
    Reflection[] findReflectionsByUidAndVid(String uid,Integer vid);

//    /**
//     * 查看某用户某视频某时间点（秒数）有没有笔记
//     * 未抛异常就是有笔记，
//     */
//    void findNoteByUidVidAndSecondTime(Note notes);
    Integer updateReflection(Reflection reflection,String fromDbsTime) throws ParseException;
}
