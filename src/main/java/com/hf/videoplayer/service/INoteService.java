package com.hf.videoplayer.service;

import com.hf.videoplayer.entity.Note;

public interface INoteService {
    /**
     * 创建一条笔记
     * @param note
     */
    void createNote(Note note);

    /**
     * 删除一条笔记
     * @param uid 用户id
     * @param vid 视频id
     * @param secondTime 时间秒
     */
    void deleteNote(String uid,Integer vid,Integer secondTime);

    /**
     * 查询某用户某视频的所有笔记
     * @param uid
     * @param vid
     * @return 返回数组长度为0 代表没有
     */
    Note[] findNotesByUidAndVid(String uid,Integer vid);

//    /**
//     * 查看某用户某视频某时间点（秒数）有没有笔记
//     * 未抛异常就是有笔记，
//     */
//    void findNoteByUidVidAndSecondTime(Note notes);

    /**
     * 查找用户的所有笔记
     * @param uid
     * @return
     */
    Note[] findNotesByUid(String uid);
}
