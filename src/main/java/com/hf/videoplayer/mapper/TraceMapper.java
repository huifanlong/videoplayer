package com.hf.videoplayer.mapper;


import com.hf.videoplayer.entity.Trace;
import com.hf.videoplayer.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TraceMapper {
    /**
     * 插入一条登录轨迹
     * @param trace 一次登录轨迹的全部数据
     * @return 受影响的行数
     */
    Integer insert(Trace trace);

    /**
     *
     * @param userName 用户名，其实可以是学号
     * @return 返回用户的轨迹列表，数组长度是0表示没有
     */
    List<Trace> findByUserName(String userName);

    /**
     * 根据轨迹记录id进行查找
     * @param id Trace表的记录id
     * @return 返回值为null代表没查到
     */
    Trace findById(Integer id);
}
