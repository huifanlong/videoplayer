package com.hf.videoplayer.service;

import com.hf.videoplayer.entity.Trace;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ITraceService {

    /**
     * 插入一条轨迹
     * @param
     */
    void createTrace(String userName,String logoutTime) throws ParseException;

    /**
     * 返回ture表示是这条记录的第一次；返回false表示不是第一次
     * 这里主要是用于进入首页时的判断要不要发动计时器
     * @param userName
     * @param totalTraces
     */
    void updateInSameLearning(String userName,String totalTraces);

    /**
     * 因为js执行顺序的问题，更改了trace的数据格式从五元一组该为三元一组；
     * in this case 如果还需要准确地记录正确退出用户的退出时间的话，那么就需要这样建一个leavingTimeSet
     * @param time
     */
    void leavingTimeSet(String userName,String time);

    /**
     *
     * @param userName 用户名，或者其实是学生学号
     * @return 该学生的所有轨迹记录
     */
    List<Trace> findByUserName(String userName);

    /**
     * 查询距今一段时间内轨迹数据
     * @param userName 用户名，或者其实是学生学号
     * @param from 最远日期
     * @return
     */
    List<Trace> findByUserName(String userName, Date from);

    /**
     * 查询从某时间到某时间的轨迹信息
     * @param userName 用户名
     * @param from 从某日期
     * @param to 到某日期
     * @return
     */
    List<Trace> findByUserName(String userName, Date from,Date to);

    /**
     * 根据id来查找轨迹记录
     * @param id
     * @return
     */
    Trace findById(Integer id);
}
