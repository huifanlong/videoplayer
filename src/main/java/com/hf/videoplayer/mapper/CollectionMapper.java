package com.hf.videoplayer.mapper;
import com.hf.videoplayer.entity.Collection;
import com.hf.videoplayer.entity.Record;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CollectionMapper {

    Integer insert(Collection collection);

    /**
     * 查看某视频某用户的收藏和喜欢状态
     * @param uid
     * @param vid
     * @return null代表未收藏且未喜欢该视频
     */
    Collection findStatusByUidAndVid(String uid,Integer vid);

    Integer delete(String uid,Integer vid);

    /**
     * 查看某用户 所有相关视频的收藏状态和喜欢状态
     * @param uid 用户名称
     * @return vid 用户收藏的视频id数组 数组长度为0代表没有该用户没有收藏或喜欢任何视频
     */
   Collection[] findStatusByUid(String uid);

    /**
     *
     * @param collection
     * @return 受影响的行数
     */
    Integer update(Collection collection);

}
