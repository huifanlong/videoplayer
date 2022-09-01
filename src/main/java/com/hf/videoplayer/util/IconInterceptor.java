package com.hf.videoplayer.util;

import com.hf.videoplayer.controller.BaseController;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//public class IconInterceptor extends BaseController implements HandlerInterceptor {
//    /**
//     * 在请求处理之前进行调用（Controller方法调用之前）
//     */
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //这里设置拦截以后重定向的页面，一般设置为登陆页面地址
//        System.out.println(request.getRequestURL());
//        System.out.println(request.getRequestURL().indexOf("video"));
//        System.out.println(request.getRequestURL().substring(request.getRequestURL().indexOf("video")));
//        System.out.println("重定向执行");
//        System.out.println("file:D:/simpletest/"+request.getRequestURL().substring(request.getRequestURL().indexOf("video")));
//        response.sendRedirect("file:D:/simpletest/"+request.getRequestURL().substring(request.getRequestURL().indexOf("video")));
//        response.sendRedirect();
//        return false;
//        return true;//如果设置为false时，被请求时，拦截器执行到此处将不会继续操作
        //如果设置为true时，请求将会继续执行后面的操作
//    }
//}
