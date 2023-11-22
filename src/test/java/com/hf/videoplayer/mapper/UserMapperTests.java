package com.hf.videoplayer.mapper;


import com.hf.videoplayer.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert(){
        User user =new User();
        user.setUserName("tim");
        user.setUserPass("123");
        user.setName("77");
        user.setAuthority(0);
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findByUserName(){
        User user = userMapper.findByUserName("test");
        System.out.println(user);
    }

//    @Test
//    public void login(){
//        String userName="kakaki";
//        String userPass="1234";
//        User user = userMapper.login(userName,userPass);
//        System.out.println(user);
//    }
    @Test
    public void finById(){
        User user = userMapper.findById(75);
        System.out.println(user);
    }
}
