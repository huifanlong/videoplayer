package com.hf.videoplayer.controller;

import com.hf.videoplayer.entity.Video;
import com.hf.videoplayer.service.ILikeService;
import com.hf.videoplayer.service.IVideoService;
import com.hf.videoplayer.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/** @RestController = @Controller + @ResponseBody*/
@RestController
@RequestMapping("likes")
public class LikeController extends BaseController{

    @Autowired
    private ILikeService likeService;

    @Autowired
    private IVideoService videoService;

    /**
     * 用户点赞时要进行两个controller方法的调用操作，分别时在videoController中给这个video的点赞数+1，以及这个方法，在like表中增加一条记录
     * @param vid 视频id
     * @param session 从中获取用户id
     * @return 添加一条点赞记录
     */
    @RequestMapping("creat_like")
    public JsonResult<Void> creatLike(Integer vid, HttpSession session){
        String uid = getUserNameFromSession(session);
        likeService.createLike(uid,vid);
        return new JsonResult<>(OK,"添加点赞记录");
    }

    /**
     * 跟上面的方法逻辑一样，只是是在用户取消点赞的时候去 删除一条记录
     * @param vid
     * @param session
     * @return
     */
    @RequestMapping("delete_like")
    public JsonResult<Void> deleteLike(Integer vid,HttpSession session){
        String uid = getUserNameFromSession(session);
        likeService.deleteLike(uid,vid);
        return new JsonResult<>(OK,"取消点赞记录");
    }

    /**
     * 用户打开视频时要调用此方法 看这个视频他是否已经点赞过，400状态码代表已点赞，其他的状态码代表未点赞
     * @param vid
     * @param session
     * @return
     */
    @RequestMapping("find_like_status")
    public JsonResult<Void> findLikeStatus(Integer vid,HttpSession session){
        String uid = getUserNameFromSession(session);
        likeService.findStatusByUidAndVid(uid,vid);
        return new JsonResult<>(OK);
    }

    /**
     * 用户查看点赞记录时调用此方法，如果返回状态时200，则用户有点赞，并可以读取返回的视频信息(video类型)；
     * 如果状态码时其他，则代表用户还未点赞任何视频
     * @param session
     * @return
     */
    @RequestMapping("find_all_liked_videos")
    public JsonResult<Video[]> findLikes(HttpSession session){
        String uid = getUserNameFromSession(session);
        Integer[] vids = likeService.findStatusByUid(uid);
        Video[] videos= new Video[vids.length];
        for(int i = 0;i < vids.length;i++) {
            videos[i] = videoService.findByVideoId(vids[i]);
        }
        return new JsonResult<>(OK,videos);
    }
}
