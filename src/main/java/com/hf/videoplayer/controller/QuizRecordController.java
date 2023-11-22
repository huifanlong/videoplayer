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
        quizRecord.setUid(uid);
        quizRecordService.creatQuizRecord(quizRecord);
        System.out.println(quizRecord);
        return new JsonResult<>(OK);

    }

    /**
     * 收到200状态码，说明已经做过
     * @param quizId
     * @param session
     * @return
     */
    @RequestMapping("is_done")
    public JsonResult<Void> isDone(Integer quizId,HttpSession session){
        String uid = getUserNameFromSession(session);
        quizRecordService.findQuizRecordByUidAndQuizID(uid,quizId);//如果没有做过，则在service层会查询不到，并会抛出其他异常
        return new JsonResult<>(OK);//如果上面没有抛出异常，则说明已经存在一条记录。抛出正确代码。
    }
}
