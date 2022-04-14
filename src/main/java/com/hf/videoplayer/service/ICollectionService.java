package com.hf.videoplayer.service;

import com.hf.videoplayer.entity.Collection;

public interface ICollectionService {

    void createCollection(String uid,Integer vid);

    void deleteCollection(String uid,Integer vid);

    /**
     * 根据用户id和视频id查看该视频该用户没有用收藏
     * @param uid
     * @param vid
     * @return 到底需不要返回值？如果查询不到该用户该视频的收藏记录 直接抛异常状态码给前端接收，好像不需要返回值了
     */
    void findStatusByUidAndVid(String uid,Integer vid);

    Integer[] findStatusByUid(String uid);
}
