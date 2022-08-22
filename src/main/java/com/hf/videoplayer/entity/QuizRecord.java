package com.hf.videoplayer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class QuizRecord {

    private Integer id;

    private Integer quizId;//练习id-对应于视频id；一个视频对应一个练习

    private String uid;//用户名

    private String answer;//答案

    private String rightQuestionID;

    private String errorQuestionID;

    private String startTime;

    private String endTime;

    private String usedTime;

    private Integer score;

    private Integer numOfRight;

}
