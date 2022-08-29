package com.hf.videoplayer.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebIconConfig implements WebMvcConfigurer {
    /**
     * 重写addCorsMappings()解决跨域问题
     * 配置：允许http请求进行跨域访问
     *
     * @param registry
     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        //指哪些接口URL需要增加跨域设置
//        registry.addMapping("/**")
//                //.allowedOrigins("*")//指的是前端哪些域名被允许跨域
//                .allowedOriginPatterns("*")
//                //需要带cookie等凭证时，设置为true，就会把cookie的相关信息带上
//                .allowCredentials(true)
//                //指的是允许哪些方法
//                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
//                //cookie的失效时间，单位为秒（s），若设置为-1，则关闭浏览器就失效
//                .maxAge(3600);
//    }
    /**
     * 重写addInterceptors()实现拦截器
     * 配置：要拦截的路径以及不拦截的路径
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IconInterceptor()).addPathPatterns("/**/static/icon/**");
    }
}
