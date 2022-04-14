package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.Note;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NoteMappersTests {
    @Autowired
    private NoteMapper noteMapper;

    @Test
    public void findNotesByUidAndVid(){
        Note[] notes = noteMapper.findNotesByUidAndVid("test1",1);
        System.out.println(notes.length);
        for(int i = 0;i < notes.length;i++){
            System.out.println(notes[i].getNotes());
        }
    }
    @Test
    public void insert(){
        Note note = new Note();
        note.setUid("kakaki");
        note.setVid(2);
        note.setSecondTime(70);
        note.setNotes("这是一条笔记");
        noteMapper.insert(note);
    }
    @Test
    public void delete(){
        noteMapper.delete("kakaki",2,70);
    }
    @Test
    public void findNoteByUidVidAndSecondTime(){
        Note note = noteMapper.findNoteByUidVidAndSecondTime("kakaki",2,70);
        System.out.println(note);
    }

    @Test
    public void updateNotes(){
        Note note = new Note();
        note.setUid("kakaki");
        note.setVid(2);
        note.setSecondTime(70);
        note.setNotes("这是一条笔记70");
        Integer rows = noteMapper.updateNotes(note);
        System.out.println(rows);
    }
}
