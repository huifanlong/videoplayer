package com.hf.videoplayer.service;

import com.hf.videoplayer.entity.Question;

public interface IQuestionService {

    /**
     * 根据quizId查找题目
     * @param quizId
     * @return 题目数组
     */
    Question[] findQuestions(Integer quizId);
}
