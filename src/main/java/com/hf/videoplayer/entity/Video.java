package com.hf.videoplayer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Video implements Serializable {

    private Integer id;

    private Integer courseId;

    private Integer cid;

    private String videoName;

    private String src;

    private Integer likeNumbers;

    private Integer collectNumbers;

}
