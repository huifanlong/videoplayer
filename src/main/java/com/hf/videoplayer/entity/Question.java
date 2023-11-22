package com.hf.videoplayer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Question implements Serializable {
    private Integer id;

    private Integer vid;//练习id-对应于视频id；一个视频对应一次练习

    private Integer questionId;//练习id下的题目id，一般有四个题，questionId为1-4

    private String title;//题目

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private String correctAnswer;

}
