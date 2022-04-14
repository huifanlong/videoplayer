package com.hf.videoplayer.service;

import com.hf.videoplayer.entity.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CollectionServiceTests {
    @Autowired
    private ICollectionService collectionService;

//    void creatCollection(String uid,Integer vid);
//
//    void deleteCollection(String uid,Integer vid);
//
//    Integer findCollectionByUidAndVid(String uid,Integer vid);
//
//    Integer[] findCollectionsByUid(String uid);
    @Test
    public void creatCollection(){
        collectionService.createCollection("kakaki",6);
    }
    @Test
    public void findCollectionByUidAndVid(){
        collectionService.findStatusByUidAndVid("kakaki",2);
    }
    @Test
    public void findCollectionsByUid(){
        Integer[] rows = collectionService.findStatusByUid("kakaki");
        for(int i = 0;i < rows.length;i++){
            System.out.println(rows[i]);
        }
    }
    @Test
    public void deleteCollection(){
        collectionService.deleteCollection("kakaki",2);
    }
}
