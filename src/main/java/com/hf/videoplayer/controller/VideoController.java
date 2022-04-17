package com.hf.videoplayer.controller;

import com.hf.videoplayer.entity.Video;
import com.hf.videoplayer.service.impl.VideoServiceImpl;
import com.hf.videoplayer.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("videos")
public class VideoController extends BaseController{

    @Autowired
    private VideoServiceImpl videoService;

    @RequestMapping("find_by_id")
     public JsonResult<Video> findVideoById(Integer id){
         Video video = videoService.findByVideoId(id);
//        System.out.println("dd");
         return new JsonResult<>(OK,video);
     }

    /**
     *
     * @param vid 视频id
     * @return 增加一个这个视频的点赞人数
     */
     @RequestMapping("add_like_numbers")
    public  JsonResult<Void> addLikeNumbersByVideoId(Integer vid) {
         videoService.addLikeNumbersByVideoId(vid);
         System.out.println("向数据库已经增加一条点赞");
         return new JsonResult<>(OK);
     }

    /**
     *
     * @param vid 视频的id
     * @return 增加一个这个视频的收藏人数
     */
    @RequestMapping("add_collect_numbers")
    public  JsonResult<Void> addCollectNumbersByVideoId(Integer vid) {
        videoService.addCollectNumbersByVideoId(vid);
        return new JsonResult<>(OK);
    }
    /**
     *
     * @param vid 视频id
     * @return 减少一个这个视频的点赞人数
     */
    @RequestMapping("minus_like_numbers")
    public  JsonResult<Void> minusLikeNumbersByVideoId(Integer vid) {
        videoService.minusLikeNumbersByVideoId(vid);
        System.out.println("向数据库减少一条点赞");
        return new JsonResult<>(OK);
    }

    /**
     *
     * @param vid 视频的id
     * @return 减少一个这个视频的收藏人数
     */
    @RequestMapping("minus_collect_numbers")
    public  JsonResult<Void> minusCollectNumbersByVideoId(Integer vid) {
        videoService.minusCollectNumbersByVideoId(vid);
        return new JsonResult<>(OK);
    }

}
