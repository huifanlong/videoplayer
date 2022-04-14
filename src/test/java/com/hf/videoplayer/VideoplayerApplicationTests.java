package com.hf.videoplayer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class VideoplayerApplicationTests {

    @Autowired//自动装配
    private DataSource dataSource;
    @Test
    void contextLoads() {
    }

    /**
     * HikariProxyConnection@1028574311 wrapping com.mysql.cj.jdbc.ConnectionImpl@60dcf9ec
     * 连接池
     */
    @Test
    void getConnection() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

}
