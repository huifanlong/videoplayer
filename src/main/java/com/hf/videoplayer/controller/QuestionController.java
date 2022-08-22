package com.hf.videoplayer.controller;

import com.hf.videoplayer.entity.Question;
import com.hf.videoplayer.service.IQuestionService;
import com.hf.videoplayer.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("questions")
public class QuestionController extends BaseController{
    @Autowired
    private IQuestionService questionService;

    /**
     *
     * @param quizId
     * @return 题目数组
     */
    @RequestMapping("get_quiz_questions")
    public JsonResult<Question[]> getQuestions(Integer quizId){
        Question[] questions = questionService.findQuestions(quizId);
        return new JsonResult<>(OK,questions);
    }

}
