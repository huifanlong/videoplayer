package com.hf.videoplayer.controller;

import com.hf.videoplayer.entity.QuizRecord;
import com.hf.videoplayer.service.IQuizRecordService;
import com.hf.videoplayer.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("quiz_record")
public class QuizRecordController extends BaseController{

    @Autowired
    private IQuizRecordService quizRecordService;

    /**
     *
     * @param quizRecord
     * @param session
     * @return 插入一条测试记录
     */
    @RequestMapping("create")
    public JsonResult<Void> createQuizRecord(QuizRecord quizRecord, HttpSession session){
        String uid = getUserNameFromSession(session);
        QuizRecord[] quizRecords = quizRecordService.findQuizRecordByUidAndQuizID(uid, quizRecord.getQuizId());
        if(quizRecords.length == 0){
            //尚未答过题
            quizRecord.setUid(uid);
            quizRecordService.creatQuizRecord(quizRecord);
            return new JsonResult<>(OK);
        }else{
            //答过题
            return new JsonResult<>(20);
        }
    }
    @RequestMapping("is_done")
    public JsonResult<Void> isDone(Integer quizId,HttpSession session){
        String uid = getUserNameFromSession(session);
        QuizRecord[] quizRecords = quizRecordService.findQuizRecordByUidAndQuizID(uid,quizId);
        if(quizRecords.length == 0){
            return new JsonResult<>(OK);//尚未答过题
        }else{
            return new JsonResult<>(20);//答过题
        }
    }
}
