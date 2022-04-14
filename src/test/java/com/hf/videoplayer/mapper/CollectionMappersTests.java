package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CollectionMappersTests {
    @Autowired
    private CollectionMapper collectionMapper;

    @Test
    public void insert(){
        Collection collection = new Collection();
        collection.setUid("kakaki");
        collection.setVid(2);
        collection.setIsCollect(1);
        collectionMapper.insert(collection);
    }

    @Test
    public void delete(){
        Integer rows = collectionMapper.delete("kakaki",2);

    }
    @Test
    public void findCollectionByUidAndVid(){
        Collection rows = collectionMapper.findStatusByUidAndVid("kakaki",10);
        System.out.println(rows);
    }
    @Test
    public void findCollectionByUid(){
        Collection[] rows = collectionMapper.findStatusByUid("test");
        System.out.println(rows.length);
        for(int i = 0;i < rows.length;i++){
            System.out.println(rows[i].getVid());
        }
    }
    @Test
    public void update(){
        Collection collection = new Collection();
        collection.setUid("kakaki");
        collection.setVid(3);
        collection.setIsCollect(0);
        collectionMapper.update(collection);
    }
}
