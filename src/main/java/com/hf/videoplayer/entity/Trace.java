package com.hf.videoplayer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class Trace implements Serializable {
    private Integer id;//主键

    private String userName;//用户名

    private String totalTrace;//包括index、reflection、video、notes的停留轨迹（时间对）

    private String videoIds;//视频id

    private String loginTime;//登陆时间

    private String logoutTime;//退出时间

}
