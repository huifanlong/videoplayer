package com.hf.videoplayer.service;

import com.hf.videoplayer.entity.User;
import com.hf.videoplayer.mapper.UserMapper;
import com.hf.videoplayer.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {
    @Autowired
    private IUserService iUserService;

    @Test
    public void reg(){
        /** 选中所选行 选择上方的code -- > surround with 就可以快速建立 try catch*/
        try {
            User user =new User();
            user.setUserName("ok");
            user.setUserPass("123");
            user.setName("88");
            user.setAuthority(0);
            iUserService.reg(user);
            System.out.println("ok");
        } catch (ServiceException e) {
            //获取异常的名字，自定义的
            System.out.println(e.getClass().getSimpleName());
            //获取异常的具体描述信息，是我们自定义的信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void findById(){
        User user = iUserService.findById(28);
        System.out.println(user);
    }


}
