package com.hf.videoplayer.service.impl;

import com.hf.videoplayer.entity.Like;
import com.hf.videoplayer.mapper.LikeMapper;
import com.hf.videoplayer.service.ILikeService;
import com.hf.videoplayer.service.ex.DeleteException;
import com.hf.videoplayer.service.ex.LikeCreateDulplicatedException;
import com.hf.videoplayer.service.ex.LikeCreateException;
import com.hf.videoplayer.service.ex.LikeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements ILikeService {
    @Autowired
    private LikeMapper LikeMapper;

    /**
     * 当用户点击点赞时，根据用户id和视频id来添加一条点赞记录
     * @param uid 用户id
     * @param vid 视频id
     */
    @Override
    public void createLike(String uid, Integer vid) {
        /*
        查询是否存在一条记录，没有才能插入。若存在，其值是0，则修改为1，
         */
        Like result = LikeMapper.findStatusByUidAndVid(uid,vid);
        if(result != null){
            throw new LikeCreateDulplicatedException("点赞记录已经存在，创建失败");
        }
        Like Like = new Like();
        Like.setIsLike(1);
        Like.setUid(uid);
        Like.setVid(vid);
        Integer rows = LikeMapper.insert(Like);
        if(rows != 1 ){
            throw new LikeCreateException("点赞失败");
        }
    }

    /**
     * 当用户取消点赞时 根据用户id和视频id去删除一条点赞记录
     * 但是其实也可以不删除，直接将其id_collect修改为0即可，这样可以方便保留用户取消点赞的视频信息，这些视频虽然取消了点赞但是可能对分析用户来说还有作用
     * 但是这样的话，就需要修改findLikeByUidAndVid()方法的mapper层sql语句，增加判断is_collect=1这个条件
     * @param uid 用户id
     * @param vid 视频id
     */

    @Override
    public void deleteLike(String uid, Integer vid) {
        Like result = LikeMapper.findStatusByUidAndVid(uid,vid);
        if(result == null){
            throw new LikeNotFoundException("点赞记录不存在");
        }
        Integer rows = LikeMapper.delete(uid,vid);
        if(rows != 1){
            throw new DeleteException("取消点赞失败");
        }

    }

    /**
     * 当初次进入视频时，调用这个方法查看该视频这个用户是否已经点赞，来更改点赞图表样式
     * @param uid 用户id
     * @param vid 视频id
     * @return 该视频的点赞状态 1 是代表已点赞.0表示未点赞
     */
    @Override
    public void findStatusByUidAndVid(String uid, Integer vid) {
        Like result = LikeMapper.findStatusByUidAndVid(uid,vid);
        if(result == null){//要是找不到 返回的是null
            throw new LikeNotFoundException("未点赞");
        }
    }

    /**
     * 当用户查看他的点赞列表时，调用此方法获得他所点赞的视频id数组，再来展示他的点赞列表
     * @param uid 用户id
     * @return 该用户所点赞的视频id数组 如果为空 代表没有点赞列表
     */
    @Override
    public Integer[] findStatusByUid(String uid) {
        Like[] result = LikeMapper.findStatusByUid(uid);
        if(result.length == 0){//要不找不到 Like数组的长度是0
            throw new LikeNotFoundException("点赞记录不存在");
        }
        Integer[] vid = new Integer[result.length];
        for(int i = 0;i < result.length;i++){
            vid[i] = result[i].getVid();
        }
        return vid;
    }
}
