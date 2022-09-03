package com.hf.videoplayer.controller;

import com.hf.videoplayer.entity.Reflection;
import com.hf.videoplayer.service.IReflectionService;
import com.hf.videoplayer.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("reflection")
public class ReflectionController extends BaseController{
    @Autowired
    private IReflectionService reflectionService;

    @RequestMapping("create")
    public JsonResult<Void> createReflections(Reflection reflection,HttpSession session){
        String uid = getUserNameFromSession(session);
        reflection.setUid(uid);
        reflectionService.createReflection(reflection);
        return new JsonResult<>(OK);
    }
    @RequestMapping("find_all")
    public JsonResult<Reflection[]> findAllReflections(HttpSession session){
        String uid = getUserNameFromSession(session);
        Reflection[] reflections = reflectionService.findReflectionsByUid(uid);
        return new JsonResult<>(OK,reflections);
    }
    @RequestMapping("find_by_video")
    public JsonResult<Reflection[]> findVideoNote(Integer vid,HttpSession session){
        String uid = getUserNameFromSession(session);
//        System.out.println(uid+"查看笔记");
        Reflection[] reflections = reflectionService.findReflectionsByUidAndVid(uid,vid);
        return new JsonResult<>(OK,reflections);
    }
    @RequestMapping("update")
    public JsonResult<Void> updateReflection(Reflection reflection){
        reflectionService.updateReflection(reflection);
        return new JsonResult<>(OK);
    }
    @RequestMapping("delete")
    public JsonResult<Void> deleteReflection(Integer id){
        reflectionService.deleteReflections(id);
        return new JsonResult<>(OK);
    }
}
