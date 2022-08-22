package com.hf.videoplayer.mapper;

import com.hf.videoplayer.entity.QuizRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuizRecordMapper {

    /**
     *
     * @param quizRecord 答题记录
     * @return 受影响的行数
     */
    Integer insert(QuizRecord quizRecord);

    /**
     *
     * @param quizId
     * @param uid
     * @return 返回某学生关于某条视频的答题记录，没有查到就返回null（假定一个学生一个视频可以有多条答题记录）
     */
    QuizRecord[] findQuizRecordByUidAndQuizId(Integer quizId,String uid);

    /**
     *
     * @param uid
     * @return 返回某学生的所有视频下的答题记录，没有查到就返回null
     */
    QuizRecord[] findUserQuizRecords(String uid);

    /**
     *
     * @param quizId
     * @return 返回某视频的所有学生答题记录，没有查到就返回null
     */
    QuizRecord[] findVideoQuizRecords(Integer quizId);
}
