package com.hf.videoplayer.controller;

import com.hf.videoplayer.service.ITraceService;
import com.hf.videoplayer.util.JsonResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("trace")
public class TraceController extends BaseController {

    @Autowired
    private ITraceService traceService;

    private ConcurrentHashMap<String,Timer> timers = new ConcurrentHashMap<>(20,(float)0.75);//用户存储用户（key）与这次登录的计时器（timer），主要用于在index页面判断是否是初次访问，初次访问才需要构造计时器，。

    /**
     * 每次离开一个页面 就调用这个方法 ，追加或新增用户的trace
     * @param session 获取用户
     * @param trace 前端传入的轨迹字符串
     * @return
     */
//    @RequestMapping("update")
//    public JsonResult<Void> update(HttpSession session,String trace){
////        System.out.println("用户："+getUserNameFromSession(session)+"轨迹："+trace);
//        traceService.updateInSameLearning(getUserNameFromSession(session),trace);
//        return new JsonResult<>(OK);
//    }

    /**
     * 登录之后需要立即调用此方法，开始计时
     * 计时器来记录用户这次登录的在线时长，具体方案：用户登录之后立即调用该请求；如果用户的session过期的话，就把当前时间当作是用户退出系统的时间。
     * session的默认过期时间是30min，一旦有新的请求，那么会重新计时。
     * task - 所要安排的任务。
     * delay - 执行任务前的延迟时间，单位是毫秒。
     * period - 执行各后续任务之间的时间间隔，单位是毫秒。
     * @param session 获取用户
     * @return
     */
    @RequestMapping("duration")
    public JsonResult<Void> onlineDuration(HttpSession session) {
//        System.out.println("已访问duration，timer是否有该用户"+timers.containsKey(getUserNameFromSession(session)));
        String userName = getUserNameFromSession(session);
        if(!timers.containsKey(userName)) {//是初次访问
            Timer timer = new Timer();
            timers.put(userName, timer);
            traceService.beginTrace(userName);
            timer.schedule(new TimerTask() {
                @SneakyThrows
                @Override
                public void run() {
                    // 使用session的getLastAccessedTime()判断最后一次访问该session的时间,其返回的是距离1970-1-1 0:0:0的毫秒数
                    long time = System.currentTimeMillis() - session.getLastAccessedTime();
//                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if (time > 1000 * 60 * 2) {//三十分钟,就可以判断用户已经退出，可以生成一条轨迹记录1
//                        traceService.createTrace(userName, dateformat.format(session.getLastAccessedTime()));
                        System.out.println(userName+"：判定退出");
                        traceService.endTrace(userName,session.getLastAccessedTime());
                        timer.cancel();
                        timers.remove(userName);
                    } else {
//                        System.out.println("session的名字" + userName);
//                        System.out.println("session的使用时间" + time);
                    }
                }
            }, 1000, 1000 * 60 * 1);//每15分钟判断一次，其实三十分钟以内判断一次效果都是一样的。
            System.out.println(getUserNameFromSession(session)+"：初次访问，已激活trace计时器。当前在线用户数："+timers.size());
        }
//        else{
//            System.out.println("不是初次访问，未激活计时器");
//        }
        return new JsonResult<>(OK);
    }
    /**
     * 离开每个页面时将数据保存在这里
     * @param session
     * @param where
     * @return
     */
    @RequestMapping("create")
    public JsonResult<Void> leaving(String where,HttpSession session){
        traceService.createTrace(getUserNameFromSession(session),where);
        return new JsonResult<>(OK);
    }

}
