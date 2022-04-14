package com.hf.videoplayer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/**
 * 指定mapper路径
 */
@MapperScan("com.hf.videoplayer.mapper")
public class VideoplayerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoplayerApplication.class, args);
    }

}
