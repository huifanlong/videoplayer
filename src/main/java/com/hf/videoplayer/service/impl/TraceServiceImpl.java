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
    private ConcurrentHashMap<String,List<String>> userLeavingTraces = new ConcurrentHashMap<String,List<String>>(20,(float)0.75);//存储用户的退出轨迹数据

    @Override
    public void createTrace(String userName,String logoutTime) throws ParseException {
        Set<String> keys = userTraces.keySet();
        keys.forEach(System.out::println);
        if(!userTraces.containsKey(userName)){
            throw new InsertException("用户轨迹没有存储成功,轨迹记录创建失败");
        }
        String totalTrace = userTraces.get(userName);//从HashMap中取出用户的轨迹
        String[] tracespics = totalTrace.split(" ");//["index_trace","**/**/**","**:**:**",""]，两个元素才组成一个日期时间。某处的登陆退出时间有三个元素组成。切割出loginTime和logoutTime
        SimpleDateFormat ft1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String loginTime = ft2.format(ft1.parse(tracespics[1]+" "+tracespics[2]));//第二个是登陆时间，还要将其从前端传来的格式1转换成格式2，格式2才能插入到数据库中的date类型数据中。如果在数据库中设置成varchar类型，其实就不需要转换
        List<String> leavingTrace = userLeavingTraces.get(userName);
        Date finalVisitingTimeRecorded = ft1.parse(tracespics[tracespics.length-2]+" "+tracespics[tracespics.length-1]);//userTrace的最后访问某页面事件点，必定是有的
        Date finalLeavingTimeRecorded = finalVisitingTimeRecorded;//放置leavingTrace没有记录，先令其等于最后访问记录，使其至少不能通过下面的第一个else if语句。
        if(leavingTrace != null){//如果leavingTrace不为空-----不能判断其size是否大于0，为空时这个方法就执行不了
            finalLeavingTimeRecorded = ft1.parse(leavingTrace.get(leavingTrace.size()-1)); //leavingTrace所记录的最后的离开时间
        }
//        System.out.println("保存用户轨迹："+totalTrace); //用户轨迹
//        for(int i = 0;i<tracespics.length;i++){
//            System.out.println(tracespics[i]);
//        }
        if(tracespics.length % 3 != 0){//如果用户最后完整的退出，就用前端最后页面的退出时间--现在其实就使用不到用户的退出时间了
            System.out.println(tracespics.length);
            throw new InsertException("用户轨迹格式不正确");
        }else if(finalLeavingTimeRecorded.after(finalVisitingTimeRecorded)){//正确退出 则用历来数组的最后一个时间作为退出时间，判断正确退出的条件：leavingTrace最后时间小于userTrace最后的时间
//            totalTrace +=""+ft1.format(finalLeavingTimeRecorded);//补充退出时间;其实也没有必要补充了 因为修改trace的形式之后，trace怎么样都不会有退出时间的。退出时间直接记录在‘退出时间’字段中
            logoutTime = ft2.format(finalLeavingTimeRecorded);//修改退出时间
        }else{//否则就是用这个方法传来的参数，即session的最后获取时间作为退出时间------更新：其实最好把上次session上次访问时间加上计时器循环统计一次的时间作为退出时间，这样就是认为用户打开一个页面，长时间没操作的话，就当他在这个页面停留了一次检查的时间，如15分钟？
//            totalTrace +=" "+ft1.format(ft2.parse(logoutTime).getTime()+1000*60*1);//同时把退出时间追加完整,以前端统一的这种形式"yyyy/MM/dd HH:mm:ss"
            logoutTime = ft2.format(ft2.parse(logoutTime).getTime()+1000*60*1);//修改退出时间
        }
//        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//         Date date = dateformat .parse(time);
//        for(int i = 0;i<tracespics.length;i = i +3){
//
//            switch(tracespics[i]){
//                case "index_trace" : indexTrace += tracespics[i+1]+" "+tracespics[i+2];break;
//                case "reflection_trace" : reflectionTrace += tracespics[i+1]+" "+tracespics[i+2];break;
//                case "video_trace" : videoTrace += tracespics[i+1]+" "+tracespics[i+2];break;
//                case "notes_trace" : notesTrace += tracespics[i+1]+" "+tracespics[i+2];break;
//                default:throw new InsertException("轨迹数据有误，创建失败");
//            }
//        }
        Trace trace = new Trace();
        trace.setUserName(userName);
        trace.setTotalTrace(totalTrace);
        trace.setLoginTime(loginTime);
        trace.setLogoutTime(logoutTime);
        Integer result = traceMapper.insert(trace);
        if(result != 1){
            throw new InsertException("轨迹记录创建失败");
        }
        userTraces.remove(userName);//在存储所有用户的进入页面轨迹的HashMap中消除该用户的这一条轨迹
        userLeavingTraces.remove(userName);//在存储所有用户的退出页面轨迹的HashMap中消除该用户的一条轨迹
    }

    @Override
    public void updateInSameLearning(String userName, String totalTraces) {
        if(userTraces.containsKey(userName)){//已有用户轨迹数据，则追加
            totalTraces = userTraces.get(userName) + totalTraces + " ";
            userTraces.put(userName,totalTraces);
        }else{
            userTraces.put(userName,totalTraces);//还没有，则说明是第一次登录，开始记录的是第一次的登陆时间："xxx_trace **/**/** **:**:**"
        }
    }

    @Override
    public void leavingTimeSet(String userName,String time) {
        if(userLeavingTraces.containsKey(userName)){//已经为这个用户添加进了HashMap<String userName,List<String>>中
             userLeavingTraces.get(userName).add(time);//则获取这个用户的List<String>,并把传入的退出时间加进去
        }else{//第一次记录该用户的退出轨迹
            List<String> leavingTraces = new ArrayList<>();
            leavingTraces.add(time);
           userLeavingTraces.put(userName,leavingTraces);//将其姓名和创建好的list<>加入到离开轨迹HashMap中
//            System.out.println("添加一条离开时间"+time+"到leavingTrace");
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
