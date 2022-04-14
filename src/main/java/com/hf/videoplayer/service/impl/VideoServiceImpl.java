package com.hf.videoplayer.service.impl;

import com.hf.videoplayer.entity.Video;
import com.hf.videoplayer.mapper.VideoMapper;
import com.hf.videoplayer.service.IVideoService;
import com.hf.videoplayer.service.ex.VideoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements IVideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public Video findByVideoId(Integer id) {
        Video video = videoMapper.findByVideoId(id);
        if(video == null){
            throw new VideoNotFoundException("视频文件找不到");
        }
        video.setId(null);//把不必要的信息置空，还有点赞人数和收藏人数 要不要传出去？
        return video;
    }

    @Override
    public void addLikeNumbersByVideoId(Integer id) {
        /**
         * 根据视频id查找视频是否存在，存在则增加一个点赞人数
         */
        Video video = videoMapper.findByVideoId(id);
        if(video == null){
            throw new VideoNotFoundException("视频文件找不到");
        }
        Integer likeNumbers = video.getLikeNumbers()+1;
        videoMapper.updateLikeNumbersByVideoId(likeNumbers,id);

    }

    @Override
    public void addCollectNumbersByVideoId(Integer id) {
        /**
         * 根据视频id查找视频是否存在，存在则增加一个收藏人数
         */
        Video video = videoMapper.findByVideoId(id);
        if(video == null){
            throw new VideoNotFoundException("视频文件找不到");
        }
        Integer collectNumbers = video.getCollectNumbers()+1;
        videoMapper.updateCollectNumbersByVideoId(collectNumbers,id);

    }

    @Override
    public void minusLikeNumbersByVideoId(Integer id) {
        /**
         * 根据视频id查找视频是否存在，存在则减少一个点赞人数
         */
        Video video = videoMapper.findByVideoId(id);
        if(video == null){
            throw new VideoNotFoundException("视频文件找不到");
        }
        Integer likeNumbers = video.getLikeNumbers()-1;
        videoMapper.updateLikeNumbersByVideoId(likeNumbers,id);
    }

    @Override
    public void minusCollectNumbersByVideoId(Integer id) {
        /**
         * 根据视频id查找视频是否存在，存在则减少一个收藏人数
         */
        Video video = videoMapper.findByVideoId(id);
        if(video == null){
            throw new VideoNotFoundException("视频文件找不到");
        }
        Integer collectNumbers = video.getCollectNumbers()-1;
        videoMapper.updateCollectNumbersByVideoId(collectNumbers,id);

    }
}
