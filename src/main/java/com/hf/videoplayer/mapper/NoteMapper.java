package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.Note;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoteMapper {
    /**
     * 插入一条笔记 某用户某视频某时间点和笔记内容
     * @param note 笔记的全部数据
     * @return 受影响的行数
     */
    Integer insert(Note note);

    /**
     * 根据用户名找出该用户所有视频的所有笔记 这里返回数组的长度一定比下面的方法要多
     * @param uid
     * @return 数组长度是0代表没有
     */
    Note[] findNotesByUid(String uid);

    /**
     * 根据用户id和视频id 寻出某用户某视频所有相关笔记
     * @param uid
     * @param vid
     * @return 数组长度是0代表没有
     */
    Note[] findNotesByUidAndVid(String uid, Integer vid);

    /**
     * 查找某用户 某条视频 某时间点 有没有笔记 ；
     * 同样的时间点只能由一条笔记
     * @param uid
     * @param vid
     * @param secondTime
     * @return 如果返回值是null 则代表没有笔记
     */
    Note findNoteByUidVidAndSecondTime(String uid,Integer vid,Integer secondTime);

    /**
     * 删除某用户 某条视频的 某个时间点的 笔记
     * @param uid
     * @param vid
     * @return
     */
    Integer delete(String uid,Integer vid,Integer secondTime);

    /**
     * 修改一条笔记的内容
     * @param note
     * @return
     */
    Integer updateNotes(Note note);
}
