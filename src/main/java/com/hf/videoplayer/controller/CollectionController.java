package com.hf.videoplayer.controller;

import com.hf.videoplayer.entity.Video;
import com.hf.videoplayer.service.ICollectionService;
import com.hf.videoplayer.service.IVideoService;
import com.hf.videoplayer.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/** @RestController = @Controller + @ResponseBody*/
@RestController
@RequestMapping("collects")
public class CollectionController extends BaseController{
    @Autowired
    private ICollectionService collectionService;

    @Autowired
    private IVideoService videoService;

    /**
     * 用户收藏时要进行两个controller方法的调用操作，分别时在videoController中给这个video的收藏数+1，以及这个方法，在collection表中增加一条记录
     * @param vid 视频id
     * @param session 从中获取用户id
     * @return 添加一条收藏记录
     */
    @RequestMapping("creat_collection")
    public JsonResult<Void> creatCollection(Integer vid, HttpSession session){
        String uid = getUserNameFromSession(session);
        collectionService.createCollection(uid,vid);
        return new JsonResult<>(OK,"添加收藏记录");
    }

    /**
     * 跟上面的方法逻辑一样，只是是在用户取消收藏的时候去 删除一条记录
     * @param vid
     * @param session
     * @return
     */
    @RequestMapping("delete_collection")
    public JsonResult<Void> deleteCollection(Integer vid,HttpSession session){
        String uid = getUserNameFromSession(session);
        collectionService.deleteCollection(uid,vid);
        return new JsonResult<>(OK,"取消收藏记录");
    }

    /**
     * 用户打开视频时要调用此方法 看这个视频他是否已经收藏过，400状态码代表已收藏，其他的状态码代表未收藏
     * @param vid
     * @param session
     * @return
     */
    @RequestMapping("find_collection_status")
    public JsonResult<Void> findCollectionStatus(Integer vid,HttpSession session){
        String uid = getUserNameFromSession(session);
        collectionService.findStatusByUidAndVid(uid,vid);
        return new JsonResult<>(OK);
    }

    /**
     * 用户查看收藏记录时调用此方法，如果返回状态时200，则用户有收藏，并可以读取返回的视频信息(video类型)；
     * 如果状态码时其他，则代表用户还未收藏任何视频
     * @param session
     * @return
     */
    @RequestMapping("find_all_collected_videos")
    public JsonResult<Video[]> findCollections(HttpSession session){
        String uid = getUserNameFromSession(session);
        Integer[] vids = collectionService.findStatusByUid(uid);
        Video[] videos= new Video[vids.length];
        for(int i = 0;i < vids.length;i++) {
            videos[i] = videoService.findByVideoId(vids[i]);
        }
        return new JsonResult<>(OK,videos);
    }
}
