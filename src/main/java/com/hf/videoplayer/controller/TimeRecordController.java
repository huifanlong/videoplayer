package com.hf.videoplayer.controller;

import com.hf.videoplayer.service.ITimeRecordService;
import com.hf.videoplayer.util.JsonResult;
import com.hf.videoplayer.entity.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("time_records")
public class TimeRecordController extends BaseController{
    @Autowired
    private ITimeRecordService timeRecordService;

    @RequestMapping("list_video_records")
    public JsonResult<Record[]> listVideoRecords(Integer vid){
        Record[] timeRecords = timeRecordService.findTimeRecordsByVid(vid);
        return new JsonResult<>(OK,timeRecords);
    }
}
