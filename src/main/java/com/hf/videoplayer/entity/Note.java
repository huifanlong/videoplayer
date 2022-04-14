package com.hf.videoplayer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Note implements Serializable {
    private Integer id;

    private String uid;//在这个表中，uid实际上是user表的user_name

    private Integer vid;

    private Integer secondTime;

    private String title;

    private String notes;
}
