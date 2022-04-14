package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /**
     *
     * @param user
     * @return 受影响的行数
     */
    Integer insert(User user);

    /**
     *
     * @param userName
     * @return 返回用户数据，没有查到就返回null
     */
    User findByUserName(String userName);

//    /**
//     *
//     * @param userName 账号
//     * @param userPass 密码
//     * @return User 将一些数据信息返回给前端
//     */
//    User login(String userName,String userPass);

    User findById(Integer id);
}
