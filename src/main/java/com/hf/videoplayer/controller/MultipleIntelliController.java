package com.hf.videoplayer.controller;

import com.hf.videoplayer.entity.MultipleIntelligence;
import com.hf.videoplayer.service.IMultipleIntelliService;
import com.hf.videoplayer.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("multiple_intelli")
public class MultipleIntelliController extends BaseController{
    @Autowired
    private IMultipleIntelliService multipleIntelliService;

    @RequestMapping("insert")
    public JsonResult<Void> insertMultiple(MultipleIntelligence multipleIntelligence, HttpSession session){
        multipleIntelligence.setUid(getUserNameFromSession(session));
        multipleIntelliService.createMultipleIntelli(multipleIntelligence);
        return new JsonResult<>(OK);
    }
}