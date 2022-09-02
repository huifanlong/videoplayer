package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.Note;
import com.hf.videoplayer.entity.Reflection;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReflectionMapper {
    /**
     * 插入一条反思
     * @param reflection 笔记的全部数据
     * @return 受影响的行数
     */
    Integer insert(Reflection reflection);

    /**
     * 根据用户名找出该用户所有视频的所有反思
     * @param uid
     * @return 数组长度是0代表没有
     */
    Reflection[] findReflectionsByUid(String uid);

    /**
     * 根据用户id和视频id 寻出某用户某视频所有相关反思
     * @param uid
     * @param vid
     * @return 数组长度是0代表没有
     */
    Reflection[] findReflectionsByUidAndVid(String uid, Integer vid);

    /**
     * 查找某用户 某条视频 某时间点 有没有笔记 ；
     * 同样的时间点只能由一条笔记
     * @param uid
     * @param time
     * @return 如果返回值是null 则代表没有笔记
     */
    Reflection findReflectionByUidAndTime(String uid,String time);

    /**
     * 删除某用户 某条视频的 某个时间点的 笔记
     * @param uid
     * @param
     * @return
     */
    Integer delete(String uid,String time);

    /**
     * 修改一条反思的内容
     * @param reflection
     * @return
     */
    Integer updateReflection(String reflection,String title,String time,Integer id);
}
