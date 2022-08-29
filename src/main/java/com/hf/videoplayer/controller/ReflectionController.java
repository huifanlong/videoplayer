package com.hf.videoplayer.controller;

import com.hf.videoplayer.entity.Reflection;
import com.hf.videoplayer.service.IReflectionService;
import com.hf.videoplayer.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.ParseException;

@RestController
@RequestMapping("reflection")
public class ReflectionController extends BaseController{
    @Autowired
    private IReflectionService reflectionService;

    @RequestMapping("create")
    public JsonResult<Void> createReflections(Reflection reflection, String state, String fromDbsTime,HttpSession session) throws ParseException {
        String uid = getUserNameFromSession(session);
        reflection.setUid(uid);
//        System.out.println(state);
        if(state.startsWith("new")){
            if(!state.endsWith("delete")){
                reflectionService.createReflection(reflection);//如果new的，且最后没有删除，则直接创建
            }
        }else if(state.startsWith("fromdbs")){
            if(state.endsWith("delete")){
                reflectionService.deleteReflections(uid,fromDbsTime);
            }else if(state.endsWith("update")){
                reflectionService.updateReflection(reflection,fromDbsTime);
            }
        }
//        System.out.println("已访问ReflectionController");
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
}
