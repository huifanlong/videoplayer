package com.hf.videoplayer.service;

import com.hf.videoplayer.entity.Video;

public interface IVideoService {

    Video findByVideoId(Integer id);

    Video[] findAllVideos();

    /**
     * 根据视频id查找视频是否存在，存在则增加一个点赞人数
     */
    void addLikeNumbersByVideoId(Integer id);

    /**
     * 根据视频id查找视频是否存在，存在则增加一个收藏人数
     */
    void addCollectNumbersByVideoId(Integer id);

    /**
     * 根据视频id查找视频是否存在，存在则减少一个点赞人数
     */
    void minusLikeNumbersByVideoId(Integer id);

    /**
     * 根据视频id查找视频是否存在，存在则减少一个收藏人数
     */
    void minusCollectNumbersByVideoId(Integer id);

}
