package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.Question;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    /**
     *
     * @param question
     * @return 受影响的行数
     */
    Integer insert(Question question);

    /**
     *
     * @param quizId
     * @return 返回练习下的所有题目，没有查到就返回null
     */
    Question[] findByQuizID(Integer quizId);
}
