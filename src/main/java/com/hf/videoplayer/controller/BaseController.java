package com.hf.videoplayer.controller;

import com.hf.videoplayer.service.ex.*;
import com.hf.videoplayer.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/** 控制层类的捕获返回类
 * 是其他类的父类*/
public class BaseController {
    /** 操作成功的状态码*/
    public static final int OK = 200;

    /** 下面这个方法的返回值就是需要传递给前端的参数
     * 当项目中产生异常，则这个方法直接来捕获异常，不需要controller里面再进行处理了，返回的数据也是直接传给前端的*/
    @ExceptionHandler(ServiceException.class) //用于统一处理抛出的异常,指定其是service异常
    public JsonResult<Void> handleException(Throwable e){//自动将异常对象传递给此方法的参数列表
        JsonResult<Void> result = new JsonResult<>(e);//不太清楚这里为什么可以将异常直接赋值给result？
        if(e instanceof UsernameDuplicatedException){
            result.setState(4000);
            result.setMessage("用户名已经被占用");
        }else if(e instanceof InsertException){
            result.setState(3000);
            result.setMessage("注册时发生未知的异常");
        }else if(e instanceof DeleteException){
            result.setState(3001);
            result.setMessage("删除失败");
        }else if(e instanceof UserNotFoundException){
            result.setState(4001);
            result.setMessage("账号不存在");
        }else if(e instanceof PasswordNotMatchException){
            result.setState(4002);
            result.setMessage("密码错误");
        }else if(e instanceof VideoNotFoundException){
            result.setState(5001);
            result.setMessage("视频文件找不到");
        }else if(e instanceof CollectionNotFoundException){
            result.setState(6001);
            result.setMessage("收藏记录不存在或未收藏");
        }else if(e instanceof CollectionCreatException){
            result.setState(6002);
            result.setMessage("收藏失败");
        }else if(e instanceof CollectionCreateDulplicatedException ){
            result.setState(6003);
            result.setMessage("收藏记录已存在，收藏失败");
        }else if(e instanceof NoteCreateFailedException){
            result.setState(7001);
            result.setMessage("笔记创建失败");
        }else if(e instanceof NoteNotExistException){
            result.setState(7002);
            result.setMessage("该视频下没有笔记");
        }else if(e instanceof NoteDeleteFailedException){
            result.setState(7003);
            result.setMessage("笔记删除失败");
        }else if(e instanceof LikeNotFoundException){
            result.setState(8001);
            result.setMessage("点赞记录不存在或未点赞");
        }else if(e instanceof LikeCreateException){
            result.setState(8002);
            result.setMessage("点赞失败");
        }else if(e instanceof LikeCreateDulplicatedException){
            result.setState(8003);
            result.setMessage("点赞记录以及存在，创建失败");
        }else if(e instanceof RecordUpdateException){
            result.setState(9002);
            result.setMessage("视频观看记录存贮时发生未知异常");
        }else if(e instanceof NoTraceException){
            result.setState(4003);
            result.setMessage("查询不到用户的轨迹");
        }
        return result;
    }

    protected final Integer getIdFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("id").toString());
    }

    protected final String getUserNameFromSession(HttpSession session){
        return session.getAttribute("userName").toString();
    }
    protected final String getNameFromSession(HttpSession session){
        return session.getAttribute("name").toString();
    }
}
