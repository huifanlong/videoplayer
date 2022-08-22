package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuestionMapperTests {
    @Autowired
    private QuestionMapper questionMapper;

    @Test
    public void insertTest(){
        Question question = new Question();
        question.setQuizId(100);
        question.setQuestionId(100);
        question.setTitle("xxoo");
        question.setOptionA("A");
        question.setOptionB("B");
        question.setOptionC("C");
        question.setOptionD("D");
        question.setCorrectAnswer("A");
        questionMapper.insert(question);
    }

    @Test
    public void findByQuizId(){
        Integer quizID = 1000;
        Question[] questions = questionMapper.findByQuizID(quizID);
        if(questions.length>0){
            for(int i=0;i<questions.length;i++){
                System.out.println(questions[i].getTitle());
            }
        }
    }
}
