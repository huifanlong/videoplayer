package com.hf.videoplayer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class Record implements Serializable {

    private Integer id;

    private String uid;//在这个表中，uid实际上是user表的user_name

    private String vid;

    private String time;

    private String rate;

    private Date createTime;

    private Integer watchTimes;
}
