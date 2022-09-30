package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.MultipleIntelligence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MultipleIntelliMapperTests {
    @Autowired
    private MultipleIntelliMapper multipleIntelliMapper;

    @Test
    public void insert(){
        MultipleIntelligence multipleIntelligence=new MultipleIntelligence();
        multipleIntelligence.setUid("uid");
        multipleIntelligence.setInterpersonalIntelli(1);
        multipleIntelligence.setMusicIntelli(1);
        multipleIntelligence.setSelfIntelli(1);
        multipleIntelligence.setBodilyKinesIntelli(1);
        multipleIntelligence.setLinguistIntelli(1);
        multipleIntelligence.setLogicMathIntelli(1);
        multipleIntelligence.setNaturalIntelli(1);
        multipleIntelligence.setSpatialIntelli(1);
        Integer result = multipleIntelliMapper.insertMultiple(multipleIntelligence);
        System.out.println(result);
    }
}
