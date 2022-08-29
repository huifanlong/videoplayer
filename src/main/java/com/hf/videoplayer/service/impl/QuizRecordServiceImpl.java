package com.hf.videoplayer.service.impl;

import com.hf.videoplayer.entity.QuizRecord;
import com.hf.videoplayer.mapper.QuizRecordMapper;
import com.hf.videoplayer.service.IQuizRecordService;
import com.hf.videoplayer.service.ex.QuizRecordCreatException;
import com.hf.videoplayer.service.ex.QuizRecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizRecordServiceImpl implements IQuizRecordService {

    @Autowired
    QuizRecordMapper quizRecordMapper;

    @Override
    public void creatQuizRecord(QuizRecord quizRecord) {
        QuizRecord[] resultRecords = quizRecordMapper.findQuizRecordByUidAndQuizId(quizRecord.getQuizId(),quizRecord.getUid());
        if(resultRecords.length == 0){ //没有答过题
            Integer result = quizRecordMapper.insert(quizRecord);
            if(result != 1){
                throw new QuizRecordCreatException("答题记录录入错误");
            }
        }else{
            throw new QuizRecordCreatException("已经答过题");
        }
    }

    @Override
    public QuizRecord[] findQuizRecordByUidAndQuizID(String uid, Integer quizID) {
        QuizRecord[] quizRecord = quizRecordMapper.findQuizRecordByUidAndQuizId(quizID,uid);
        if(quizRecord.length == 0){
            throw new QuizRecordNotFoundException("用户尚未有该视频答题记录");
        }
        return quizRecord;
    }

    @Override
    public QuizRecord[] findUserQuizRecords(String uid) {
        QuizRecord[] quizRecord = quizRecordMapper.findUserQuizRecords(uid);
        if(quizRecord.length == 0){
            throw new QuizRecordNotFoundException("用户尚未有任何视频答题记录");
        }
        return quizRecord;
    }

    @Override
    public QuizRecord[] findVideoQuizRecords(Integer quizId) {
        QuizRecord[] quizRecord = quizRecordMapper.findVideoQuizRecords(quizId);
        if(quizRecord.length == 0){
            throw new QuizRecordNotFoundException("该视频尚未有任何用户答题记录");
        }
        return quizRecord;
    }
}
