package com.hf.videoplayer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ClickEvent {
    private Integer id;

    private String uid;//在这个表中，uid实际上是user表的user_name

    private Integer vid;

    private String event;

    private Integer position;

    private String time;

    private String state;

    private Double rate;
}
