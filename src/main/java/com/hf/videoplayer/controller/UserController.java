package com.hf.videoplayer.controller;

import com.hf.videoplayer.entity.Record;
import com.hf.videoplayer.entity.User;
import com.hf.videoplayer.service.IUserService;
import com.hf.videoplayer.service.ex.UsernameDuplicatedException;
import com.hf.videoplayer.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Timer;
import java.util.TimerTask;

/** @RestController = @Controller + @ResponseBody*/
@RestController
@RequestMapping("users")
public class UserController extends BaseController{
    @Autowired
    private IUserService userService;


    @RequestMapping("reg")
    /**在浏览器上使用这条语句可以验证该controller方法：
     * http://localhost:8080/users/reg?userName=kakaki&userPass=1234&name=12&authority=0
     * 数据匹配方式一：请求方法的参数是pojo类型
     * 这时SpringBoot会将 URL中的参数 与该pojo类的 属性 进行匹配*/
    public JsonResult<Void> reg(User user){
        //创建响应结果
        userService.reg(user);
        /**如果由异常都是让其父类去处理了*/
        return new JsonResult<Void>(OK);
    }

/**如果没有写BaseController就要写如下这些代码
    public JsonResult<Void> reg(User user){
        //创建响应结果
        JsonResult<Void> result = new JsonResult<>();
        try {
            userService.reg(user);
            result.setState(200);
            result.setMessage("注册成功");
        } catch (UsernameDuplicatedException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        } catch (InsertException e){
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }
        return result;
    }
 */

   @RequestMapping("login")
    /**浏览器中测试：http://localhost:8080/users/login?userName=kakaki&userPass=1234
     * 数据接收方式二：请求方法的参数列表不是pojo类
     * 这时SpringBoot会将前端 URL中的参数 与请求方法中的 参数 进行比较*/
   public JsonResult<User> login(String userName, String userPass, HttpSession session){
       User user = userService.login(userName,userPass);
       /** 封装session*/
       session.setAttribute("id",user.getId());
       session.setAttribute("userName",user.getUserName());
       session.setAttribute("name",user.getName());

//       System.out.println(getIdFromSession(session));
//       System.out.println(getUserNameFromSession(session));
//       System.out.println(getNameFromSession(session));

       return new JsonResult<User>(OK,user);
   }

   @RequestMapping("find_by_id")
    public JsonResult<User> findById(HttpSession session){
//       System.out.println("根据id查找用户：find_by_id的controller层测试");
       User user = userService.findById(getIdFromSession(session));
       return new JsonResult<>(OK,user);
   }

   @RequestMapping("login_out")
    public JsonResult<Void> logout(HttpSession session){
       session.setAttribute("id",null);
       session.setAttribute("userName",null);
       System.out.println("ddd");
       return new JsonResult<>(OK);
   }

    /**
     * 创建一条用户的视屏观看轨迹记录
     * @param record record表结构
     * @param session 从中过去用户id
     * @return 不需要特比的前端内容接收
     */
   @RequestMapping("create_record")
    public  JsonResult<Void> createRecord(Record record,HttpSession session){
       String uid= getUserNameFromSession(session);//Record数据表里面的uid实际上是用户名userName
       record.setUid(uid);
       userService.creatRecord(record);
       System.out.println("Record success");
       return new JsonResult<>(OK);
   }
}
