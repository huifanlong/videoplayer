package com.hf.videoplayer.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TraceServiceTests {
    @Test
    public void SimpleDateFormatTest() throws ParseException {
        SimpleDateFormat ft1 = new SimpleDateFormat("yyyy-MM-dd ahh:mm:ss");
        SimpleDateFormat ft2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat ft3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat ft4 = new SimpleDateFormat("yyyy-MM-dd aHH:mm:ss");
        Date date;
//        date = ft2.parse("2019/12/25 下午2:58:18");
//        System.out.println(ft3.format(date));
        try {
            date = ft2.parse("2022/9/2 下午12:42:52");
            System.out.println("yyyy/MM/dd HH:mm:s");
        } catch (ParseException e){
            try {
                date = ft1.parse("2022/9/2 下午12:42:52");
                System.out.println("yyyy/MM/dd ahh:mm:ss");
            } catch (ParseException ex) {
                date = ft4.parse("2022/9/2 下午12:42:52");
                System.out.println("yyyy/MM/dd aHH:mm:ss");
            }
        }
        System.out.println(ft3.format(date));
    }
}
