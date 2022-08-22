package com.hf.videoplayer.service;

import com.hf.videoplayer.entity.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuestionServiceTests {
    @Autowired
    private IQuestionService questionService;

    @Test
    public void findQuestions(){
        Question[] questions = questionService.findQuestions(2);
        if(questions.length>0){
            for(int i=0;i<questions.length;i++){
                System.out.println(questions[i].getTitle());
            }
        }
    }

}
