package com.hf.videoplayer.service.impl;

import com.hf.videoplayer.entity.Record;
import com.hf.videoplayer.entity.User;
import com.hf.videoplayer.mapper.RecordMapper;
import com.hf.videoplayer.mapper.UserMapper;
import com.hf.videoplayer.service.IUserService;
import com.hf.videoplayer.service.ex.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/** 用户模块业务层的实现类*/
@Service
/** @Sevice注解 ：将当前类的对象将给Spring来管理，自动创建对象
 *  没有写的话，再进行测试的时候会报错 no such bean*/
public class UserServiceImpl implements IUserService {
    /** */

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public void reg(User user) {

        /**判断是否用户名已经存在*/
        String userName = user.getUserName();
        User result = userMapper.findByUserName(userName);
        if(result != null ){
            throw new UsernameDuplicatedException("用户名被占用");
        }
        /** 加密处理 省略*/

        /** 如果数据库中有一些其他的字段需要在注册时补全 ，则写在这里*/

        /**进行注册*/
        Integer rows = userMapper.insert(user);
        if(rows != 1){
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }
    }

    @Override
    public User login(String userName, String userPass) {
        User result = userMapper.findByUserName(userName);
        if (result == null){
            throw new UserNotFoundException("用户不存在");
        }else if(!result.getUserPass().equals(userPass)){
            throw new PasswordNotMatchException("密码错误");
        }
     /**重新构造一个user 传递前端能狗看到的一些字段、属性*/
        User user = new User();
        user.setUserName(result.getUserName());
        user.setName(result.getName());
        user.setId(result.getId());
        user.setAuthority(result.getAuthority());

        return user;
    }

    @Override
    public User findById(Integer id) {
        User result = userMapper.findById(id);
        if (result == null){
            throw new UserNotFoundException("用户不存在");
        }
        /**重新构造一个user 传递前端能狗看到的一些字段、属性*/
        User user = new User();
        user.setUserName(result.getUserName());
        user.setName(result.getName());
        user.setId(result.getId());
        return user;
    }

    @Override
    public void creatRecord(Record record) {
        String uid = record.getUid();
        String vid = record.getVid();
        System.out.println("uid"+uid);
        System.out.println("vid"+vid);
        /**根据用户id和视频id去Record表里面查找是否有记录*/
        Record result = recordMapper.findRecordByUidAndVid(uid,vid);

        record.setCreateTime(new Date());//获取当前时间

        /**如果已经有一条记录 那么就把记录的观看次数增加 并且调用update修改一条记录*/
        if(result != null){
            record.setWatchTimes(result.getWatchTimes()+1);
            Integer rows = recordMapper.update(record);
            if(rows != 1){
                throw new RecordUpdateException("观看数据更新异常");
            }
            System.out.println("Record success --not first time");
        }else{
            /**否则就是正常插入一条数据*/
            record.setWatchTimes(1);
            Integer rows = recordMapper.insert(record);
            if(rows != 1){
                throw new RecordUpdateException("观看数据存储异常");
            }
            System.out.println("Record success --first time");
        }

    }
//    http://localhost:8080/web/videoplayer_etm2021.html?src=../video/1/13590931-1-208.mp4
//    http://localhost:8080/web/videoplayer_etm2021.html?src=../\video\1\13590176-1-208.mp4

}
