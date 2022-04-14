package com.hf.videoplayer.service.impl;

import com.hf.videoplayer.entity.Collection;
import com.hf.videoplayer.mapper.CollectionMapper;
import com.hf.videoplayer.service.ICollectionService;
import com.hf.videoplayer.service.ex.CollectionCreatException;
import com.hf.videoplayer.service.ex.CollectionCreateDulplicatedException;
import com.hf.videoplayer.service.ex.CollectionNotFoundException;
import com.hf.videoplayer.service.ex.DeleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectionServiceImpl implements ICollectionService {
    @Autowired
    private CollectionMapper collectionMapper;

    /**
     * 当用户点击收藏时，根据用户id和视频id来添加一条收藏记录
     * @param uid 用户id
     * @param vid 视频id
     */
    @Override
    public void createCollection(String uid, Integer vid) {
        /*
        查询是否存在一条记录，没有才能插入。若存在，其值是0，则修改为1，
         */
        Collection result = collectionMapper.findStatusByUidAndVid(uid,vid);
        if(result != null){
            throw new CollectionCreateDulplicatedException("收藏记录已经存在，创建失败");
        }
        Collection collection = new Collection();
        collection.setIsCollect(1);
        collection.setUid(uid);
        collection.setVid(vid);
        Integer rows = collectionMapper.insert(collection);
        if(rows != 1 ){
            throw new CollectionCreatException("收藏失败");
        }
    }

    /**
     * 当用户取消收藏时 根据用户id和视频id去删除一条收藏记录
     * 但是其实也可以不删除，直接将其id_collect修改为0即可，这样可以方便保留用户取消收藏的视频信息，这些视频虽然取消了收藏但是可能对分析用户来说还有作用
     * 但是这样的话，就需要修改findCollectionByUidAndVid()方法的mapper层sql语句，增加判断is_collect=1这个条件
     * @param uid 用户id
     * @param vid 视频id
     */

    @Override
    public void deleteCollection(String uid, Integer vid) {
        Collection result = collectionMapper.findStatusByUidAndVid(uid,vid);
        if(result == null){
            throw new CollectionNotFoundException("收藏记录不存在");
        }
        Integer rows = collectionMapper.delete(uid,vid);
        if(rows != 1){
            throw new DeleteException("取消收藏失败");
        }

    }

    /**
     * 当初次进入视频时，调用这个方法查看该视频这个用户是否已经收藏，来更改收藏图表样式
     * @param uid 用户id
     * @param vid 视频id
     * @return 该视频的收藏状态 1 是代表已收藏.0表示未收藏
     */
    @Override
    public void findStatusByUidAndVid(String uid, Integer vid) {
        Collection result = collectionMapper.findStatusByUidAndVid(uid,vid);
        if(result == null){//要是找不到 返回的是null
            throw new CollectionNotFoundException("未收藏");
        }
    }

    /**
     * 当用户查看他的收藏列表时，调用此方法获得他所收藏的视频id数组，再来展示他的收藏列表
     * @param uid 用户id
     * @return 该用户所收藏的视频id数组 如果为空 代表没有收藏列表
     */
    @Override
    public Integer[] findStatusByUid(String uid) {
        Collection[] result = collectionMapper.findStatusByUid(uid);
        if(result.length == 0){//要不找不到 collection数组的长度是0
            throw new CollectionNotFoundException("收藏记录不存在");
        }
        Integer[] vid = new Integer[result.length];
        for(int i = 0;i < result.length;i++){
            vid[i] = result[i].getVid();
        }
        return vid;
    }
}
