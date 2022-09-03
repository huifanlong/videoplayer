package com.hf.videoplayer.service.impl;

import com.hf.videoplayer.entity.Trace;
import com.hf.videoplayer.mapper.TraceMapper;
import com.hf.videoplayer.service.ITraceService;
import com.hf.videoplayer.service.ex.InsertException;
import com.hf.videoplayer.service.ex.NoTraceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TraceServiceImpl implements ITraceService {

    @Autowired
    private TraceMapper traceMapper;
    private ConcurrentHashMap<String,String> userTraces = new ConcurrentHashMap<String,String>(20,(float)0.75);//存储用户的登陆轨迹数据
    private ConcurrentHashMap<String,List<Integer>> userTraceNum = new ConcurrentHashMap<String,List<Integer>>(20,(float)0.75);//存储用户登录次数（表明此次轨迹是第i次）

    @Override
    public void createTrace(String userName,String where){
        if(!userTraceNum.containsKey(userName)){
            throw new InsertException("用户轨迹没有存储成功,轨迹记录创建失败");
        }
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Trace trace = new Trace();
        trace.setUserName(userName);
//        System.out.println("用户"+userName+"登录次数"+userTraceNum.get(userName).size());
        trace.setNum(userTraceNum.get(userName).get(userTraceNum.get(userName).size()-1));
        trace.setTrace(where);
        trace.setTime(ft.format(new Date()));
        Integer result = traceMapper.insert(trace);
        if(result != 1){
            throw new InsertException("轨迹记录创建失败");
        }
    }

    @Override
    public void beginTrace(String userName) {
        if(!userTraceNum.containsKey(userName)){
            userTraceNum.put(userName,new ArrayList<>());
            userTraceNum.get(userName).add(1);
        }else{
            userTraceNum.get(userName).add(userTraceNum.get(userName).get(userTraceNum.get(userName).size()-1) +1);
        }
    }

    @Override
    public void endTrace(String userName,Long sessionLastAccessTime){
        if(!userTraceNum.containsKey(userName)){
            throw new InsertException("用户轨迹没有存储成功,轨迹记录创建失败");
        }
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Trace trace = new Trace();
        trace.setUserName(userName);
        trace.setNum(userTraceNum.get(userName).get(userTraceNum.get(userName).size()-1));
        trace.setTrace("endTrace");
        trace.setTime(ft.format(sessionLastAccessTime + 1000*60*5));
        Integer result = traceMapper.insert(trace);
        if(result != 1){
            throw new InsertException("endTrace创建失败");
        }
    }

    @Override
    public List<Trace> findByUserName(String userName) {
        List<Trace> traces = traceMapper.findByUserName(userName);
        if(traces == null){
            throw new NoTraceException("该用户没有轨迹数据");
        }
        else{//打包清空不必要数据
            for(Trace element : traces){
                element.setId(null);
            }
        }
        return traces;
    }

    @Override
    public List<Trace> findByUserName(String userName, Date from) {
        return null;
    }

    @Override
    public List<Trace> findByUserName(String userName, Date from, Date to) {
        return null;
    }

    @Override
    public Trace findById(Integer id) {
        Trace trace = traceMapper.findById(id);
        if(trace == null){
            throw new NoTraceException("找不到数据");
        }
        return trace;
    }
}
