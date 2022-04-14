package com.hf.videoplayer.controller;

import com.hf.videoplayer.entity.Note;
import com.hf.videoplayer.service.INoteService;
import com.hf.videoplayer.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/** @RestController = @Controller + @ResponseBody*/
@RestController
@RequestMapping("notes")
public class NoteController extends BaseController{

    @Autowired
    private INoteService noteService;

    /**
     * 创建笔记或删除笔记 其实是同意修改前端关闭某视频页面时传过来的多条笔记
     * @param note 前端需要传note对象的三个属性，分别是vid，secondTime以及notes
     * @param session
     * @return
     */
    @RequestMapping("create_notes")
    public JsonResult<Void> createNotes(Note note, String state, HttpSession session){
        String uid = getUserNameFromSession(session);
        note.setUid(uid);
        System.out.println(state);
        System.out.println(state.equals("new"));
//        System.out.println("创建笔记controller层的测试1");
        if(state.equals("new")){
            noteService.createNote(note);
//            System.out.println("创建笔记controller层的测试");
        }else if(state.equals("fromdbs-delete")){
            noteService.deleteNote(uid,note.getVid(), note.getSecondTime());
        }
        return new JsonResult<>(OK);
    }

    /**
     * 如果返回状态码是200，则可以解析返回的笔记；若状态码是其他则代码该视频下用户未创建笔记
     * @param vid 视频id
     * @param session
     * @return
     */
    @RequestMapping("find_video_notes")
    public JsonResult<Note[]> findVideoNote(Integer vid,HttpSession session){
        String uid = getUserNameFromSession(session);
        System.out.println(uid+"查看笔记");
        Note[] notes = noteService.findNotesByUidAndVid(uid,vid);
        System.out.println("笔记数量"+notes.length);
        for(int i = 0;i < notes.length;i++){
            System.out.println(notes[i].getNotes());
        }
        return new JsonResult<Note[]>(OK,notes);
    }

    /**
     * 如果返回状态码是200，则可以解析返回的笔记；若状态码是其他则代码该视频下用户未创建笔记
     * @param session 用户id
     * @return
     */
    @RequestMapping("find_all_notes")
    public JsonResult<Note[]> findAllNote(HttpSession session){
        String uid = getUserNameFromSession(session);
        Note[] notes = noteService.findNotesByUid(uid);
        return new JsonResult<Note[]>(OK,notes);
    }
}
