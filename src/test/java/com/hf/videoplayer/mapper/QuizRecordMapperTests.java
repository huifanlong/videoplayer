package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.QuizRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuizRecordMapperTests {

    @Autowired
    private QuizRecordMapper quizRecordMapper;

    @Test
    public void insert(){
        QuizRecord quizRecord = new QuizRecord();
        quizRecord.setQuizId(1);
        quizRecord.setAnswer("A");
        quizRecord.setUid("kakaki");
        quizRecord.setStartTime("st");
        quizRecord.setEndTime("et");
        quizRecord.setUsedTime("ut");
        quizRecord.setRightQuestionID("3,4");
        quizRecord.setErrorQuestionID("1,2");
        quizRecord.setNumOfRight(2);
        Integer result = quizRecordMapper.insert(quizRecord);
        System.out.println(result);
    }

    @Test
    public void findUserQuizRecords(){
        QuizRecord[] quizRecords = quizRecordMapper.findUserQuizRecords("2019213370");
        System.out.println(quizRecords.length);
    }

    @Test
    public void findVideoQuizRecords(){
        QuizRecord[] quizRecords = quizRecordMapper.findVideoQuizRecords(2);
        System.out.println(quizRecords.length);
    }

    @Test
    public void findQuizRecordByUidAndQuizId(){
        QuizRecord[] quizRecords = quizRecordMapper.findQuizRecordByUidAndQuizId(2,"2019213370");
        System.out.println(quizRecords.length);
    }
}
