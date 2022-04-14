package com.hf.videoplayer.service;

import com.hf.videoplayer.entity.User;
import com.hf.videoplayer.entity.Record;
import org.springframework.stereotype.Service;

/*
用户模块业务层接口
 */
public interface IUserService {
    /** 注册功能*/
    void reg(User user);

    /**d
     * 登录功能
     * 该功能不需要写mapper层，用的都是上面这个方法已经写好的
     * 返回值user可以好存在cookie或session中
     * */
    User login(String userName,String userPass);

    User findById(Integer id);

    void creatRecord(Record record);


}
