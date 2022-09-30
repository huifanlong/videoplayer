package com.hf.videoplayer.service.impl;

import com.hf.videoplayer.entity.MultipleIntelligence;
import com.hf.videoplayer.mapper.ClickEventMapper;
import com.hf.videoplayer.service.IMultipleIntelliService;
import com.hf.videoplayer.service.ex.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultipleIntelliServiceImpl implements IMultipleIntelliService {
    @Autowired
    private ClickEventMapper mapper;

    @Override
    public void createMultipleIntelli(MultipleIntelligence multipleIntelligence) {
        if(mapper.findMultiple(multipleIntelligence.getUid()).length > 0){
            throw new InsertException("已存在");
        }
        if(mapper.insertMultiple(multipleIntelligence) != 1){
            throw new InsertException("插入失败，发生系统异常");
        };
    }
}
