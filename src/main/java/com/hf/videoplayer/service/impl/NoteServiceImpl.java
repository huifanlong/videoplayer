package com.hf.videoplayer.service.impl;

import com.hf.videoplayer.entity.Note;
import com.hf.videoplayer.mapper.NoteMapper;
import com.hf.videoplayer.service.INoteService;
import com.hf.videoplayer.service.ex.NoteCreateFailedException;
import com.hf.videoplayer.service.ex.NoteDeleteFailedException;
import com.hf.videoplayer.service.ex.NoteNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl implements INoteService {
    @Autowired
    private NoteMapper noteMapper;

    /**
     * 创建一条笔记
     * @param note
     */
    @Override
    public void createNote(Note note) {
        /*
        如果该时刻的笔记已经存在，那么修改其内容
         */
            Note result = noteMapper.findNoteByUidVidAndSecondTime(note.getUid(), note.getVid(), note.getSecondTime());
            if(result != null){
//            throw new NoteThisSecondAlreadyExistException("该时刻已经有笔记，请前去修改而不是创建");其实不是可以直接在这里修改吗？
                noteMapper.updateNotes(note);
            }
            Integer rows = noteMapper.insert(note);
            if(rows != 1){//没有插入成功
                throw new NoteCreateFailedException("笔记添加失败");

            }
    }

    /**
     * 删除一条笔记
     * @param uid 用户id
     * @param vid 视频id
     * @param secondTime 时间秒
     */
    @Override
    public void deleteNote(String uid, Integer vid, Integer secondTime) {
        Note note = noteMapper.findNoteByUidVidAndSecondTime(uid,vid,secondTime);//查找有无
        if(note == null){
            throw new NoteNotExistException("笔记不存在，删除失败");
        }
        Integer rows = noteMapper.delete(uid,vid,secondTime);
        if(note == null){
            throw new NoteDeleteFailedException("删除失败");
        }

    }

    /**
     * 查询某用户某视频的所有笔记
     * @param uid
     * @param vid
     * @return 返回数组长度为0 代表没有
     */
    @Override
    public Note[] findNotesByUidAndVid(String uid, Integer vid) {
        Note[] notes = noteMapper.findNotesByUidAndVid(uid,vid);
        if(notes.length == 0){
            throw new NoteNotExistException("没有笔记");
        }
        for(int i = 0 ;i < notes.length;i++){
            notes[i].setUid(null);
            notes[i].setId(null);
            notes[i].setVid(null);
        }
        return notes;
    }

    /**
     * 查找用户的所有笔记 方法跟上面类似
     * @param uid
     * @return
     */
    @Override
    public Note[] findNotesByUid(String uid) {
        Note[] notes = noteMapper.findNotesByUid(uid);
        if(notes.length == 0){
            throw new NoteNotExistException("没有笔记");
        }
        //把一些不必要的数据设置为空
        for(int i = 0 ;i < notes.length;i++){
            notes[i].setUid(null);
            notes[i].setId(null);
            notes[i].setVid(null);
        }
        return notes;
    }

//    /**
//     * 查看某用户某视频某时间点（秒数）有没有笔记
//     * @return 返回值是null 则代表没有笔记 正常会返回1
//     */
//    @Override
//    public void findNoteByUidVidAndSecondTime(Note notes) {
//        Note note = noteMapper.findNoteByUidVidAndSecondTime(notes.getUid(), notes.getVid(), notes.getSecondTime());
//        if(note == null){
//            throw new NoteNotExistException("记录不存在");
//        }
//    }

}
