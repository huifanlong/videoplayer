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
    public JsonResult<Void> getQuestions(QuizRecord quizRecord, HttpSession session){
        String uid = getUserNameFromSession(session);
        quizRecord.setUid(uid);
        quizRecordService.creatQuizRecord(quizRecord);
        return new JsonResult<>(OK);
    }
}
