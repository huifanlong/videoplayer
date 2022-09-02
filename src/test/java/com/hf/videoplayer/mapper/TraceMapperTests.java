package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.Trace;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TraceMapperTests {
    @Autowired
    private TraceMapper traceMapper;

    @Test
    public void insert() throws ParseException {
        Trace trace = new Trace();
//        SimpleDateFormat ft1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(ft2.format(ft1.parse("2022/4/12 17:32:05")));
        trace.setUserName("kakaki");
        trace.setTime("xxx");
        trace.setTrace("xxx");
        trace.setNum(1);
//        trace.setTotalTrace("Trace");
//        trace.setVideoIds("VideoIds");
//        trace.setLoginTime(new Date());
//        trace.setLogoutTime(ft.format(new Date()));

        traceMapper.insert(trace);
    }

    @Test
    public void findByUserNameTest(){
        List<Trace> trace = traceMapper.findByUserName("kakaki");
        for(Trace element: trace){
            System.out.println(element);
        }
    }

    @Test
    public void findById(){
        Trace trace = traceMapper.findById(1);
        System.out.println(trace);
    }
}
