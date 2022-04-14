package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.Video;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VideoMapper {
    /**
     *
     * @param video
     * @return 受影响的行数
     */
    Integer insert(Video video);

    /**
     *
     * @param videoName
     * @return 返回用户数据，没有查到就返回null
     */
    Video findByVideoName(String videoName);

    Video findByVideoId(Integer id);

    /**
     *
     * @param likeNumbers 更新的点赞人数
     * @param id 视频id
     * @return 受影响的行数
     */
    Integer updateLikeNumbersByVideoId(Integer likeNumbers,Integer id);

    /**
     *
     * @param collectNumbers 更新的收藏人数
     * @param id 视频id
     * @return 受影响的行数
     */
    Integer updateCollectNumbersByVideoId(Integer collectNumbers,Integer id);
}
