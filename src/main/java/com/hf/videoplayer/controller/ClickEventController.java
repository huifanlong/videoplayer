package com.hf.videoplayer.controller;

import com.hf.videoplayer.entity.ClickEvent;
import com.hf.videoplayer.service.IClickEventService;
import com.hf.videoplayer.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.jar.JarEntry;

@RestController
@RequestMapping("click_event")
public class ClickEventController extends BaseController{
    @Autowired
    private IClickEventService clickEventService;

    @RequestMapping("create")
    public JsonResult<Void> createClickEvent(ClickEvent clickEvent, HttpSession session){
        String uid = getUserNameFromSession(session);
        clickEvent.setUid(uid);
        clickEventService.createClickEvent(clickEvent);
        return new JsonResult<>(OK);//异常的话返回5006
    }
    @RequestMapping("find_uvpair")
    public JsonResult<ClickEvent[]> findUVPair(Integer vid,HttpSession session){
        String uid = getUserNameFromSession(session);
        ClickEvent[] clickEvents = clickEventService.findUVPair(uid,vid);
        return new JsonResult<>(OK,clickEvents);//查询结果为空的话返回5007
    }

}
