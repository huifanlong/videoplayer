package com.hf.videoplayer.service;

import com.hf.videoplayer.entity.QuizRecord;

public interface IQuizRecordService {
    /**
     *
     * @param quizRecord
     */
    void creatQuizRecord(QuizRecord quizRecord);

    /**
     *
     * @param uid
     * @param quizID
     * @return 返回某学生关于某条视频的答题记录，没有查到就返回null（假定一个学生一个视频可以有多条答题记录）
     */
    QuizRecord[] findQuizRecordByUidAndQuizID(String uid,Integer quizID);

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
