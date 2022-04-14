package com.hf.videoplayer.service;

import com.hf.videoplayer.entity.Note;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NoteServiceTests {
    @Autowired
    private INoteService noteService;

    @Test
    public void createNote(){
        Note note = new Note();
        note.setUid("kakaki");
        note.setVid(2);
        note.setSecondTime(80);
        note.setNotes("这是一个笔记80");
//        noteService.createNote(state,note);
    }
    @Test
    public void deleteNote(){
        noteService.deleteNote("kakaki",2,80);

    }
    @Test
    public void findNotesByUidAndVid(){
        Note[] notes = noteService.findNotesByUidAndVid("kakaki",2);
        System.out.println(notes.length);
        for(int i = 0;i < notes.length;i++){
            System.out.println(notes[i].getNotes());
        }

    }
    @Test
    public void findNoteByUidVidAndSecondTime(){

    }
}
