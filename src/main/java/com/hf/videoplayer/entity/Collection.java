package com.hf.videoplayer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Collection implements Serializable {
    private Integer id;

    private String uid;//在这个表中，uid实际上是user表的user_name

    private Integer vid;

    private Integer isCollect;

}
