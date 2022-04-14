package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.Like;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LikeMapperTests {
    @Autowired
    private LikeMapper likeMapper;

    @Test
    public void insert(){
        Like like = new Like();
        like.setUid("kakaki");
        like.setVid(3);
        like.setIsLike(1);
        likeMapper.insert(like);
    }

    @Test
    public void delete(){
        Integer rows = likeMapper.delete("kakaki",2);

    }
    @Test
    public void findCollectionByUidAndVid(){
        Like rows = likeMapper.findStatusByUidAndVid("kakaki",2);
        System.out.println(rows);
    }
    @Test
    public void findCollectionByUid(){
        Like[] rows = likeMapper.findStatusByUid("kakaki");
        System.out.println(rows.length);
        for(int i = 0;i < rows.length;i++){
            System.out.println(rows[i].getVid());
        }
    }
    @Test
    public void update(){
        Like like = new Like();
        like.setUid("kakaki");
        like.setVid(2);
        like.setIsLike(0);
        likeMapper.update(like);
    }
}
