package com.hf.videoplayer.service.impl;

import com.hf.videoplayer.entity.Question;
import com.hf.videoplayer.mapper.QuestionMapper;
import com.hf.videoplayer.service.IQuestionService;
import com.hf.videoplayer.service.ex.QuestionsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    /**
     *
     * @param quizId 测验id（与视频id对应）
     * @return 题目数组
     */
    @Override
    public Question[] findQuestions(Integer quizId) {
        Question[] questions = questionMapper.findByQuizID(quizId);
        if(questions.length == 0){//要不找不到 collection数组的长度是0
            throw new QuestionsNotFoundException("题目找不到");
        }
        return questions; //也可以把quizID和questionID设置为空后，再返回
    }
}
